package druid

import org.apache.curator.framework.CuratorFrameworkFactory
import org.apache.curator.retry.BoundedExponentialBackoffRetry
import io.druid.granularity._
import io.druid.query.aggregation.LongSumAggregatorFactory
import com.metamx.common.Granularity
import com.metamx.tranquility.beam.{Beam, ClusteredBeamTuning}
import com.metamx.tranquility.druid._
import com.metamx.tranquility.spark.BeamFactory
import io.druid.data.input.impl.TimestampSpec
import org.joda.time.Period

class MessageBeamFactory extends BeamFactory[Message]
{
  // Return a singleton, so the same connection is shared across all tasks in the same JVM.
  def makeBeam: Beam[Message] = MessageBeamFactory.BeamInstance
}

object MessageBeamFactory
{
  val BeamInstance: Beam[Message] = {
    // Tranquility uses ZooKeeper (through Curator framework) for coordination.
    val curator = CuratorFrameworkFactory.newClient(
      "localhost:2182",
      new BoundedExponentialBackoffRetry(100, 3000, 5)
    )
    curator.start()

    val indexService = DruidEnvironment("druid/overlord") // Your overlord's druid.service, with slashes replaced by colons.
    val discoveryPath = "/druid/discovery"     // Your overlord's druid.discovery.curator.path
    val dataSource = "events_druid"
    val dimensions = IndexedSeq("countryCode", "source", "userId")
    val aggregators = Seq(new LongSumAggregatorFactory("isRetweet", "isRetweet"))

    // Expects simpleEvent.timestamp to return a Joda DateTime object.
    DruidBeams
      .builder((event: Message) => event.time)
      .curator(curator)
      .discoveryPath(discoveryPath)
      .location(DruidLocation(indexService, dataSource))
      .rollup(DruidRollup(SpecificDruidDimensions(dimensions), aggregators, QueryGranularities.MINUTE, true))
      .tuning(
        ClusteredBeamTuning(
          segmentGranularity = Granularity.HOUR,
          windowPeriod = new Period("PT1M"),
          partitions = 2,
          replicants = 1
        )
      )


      .timestampSpec(new TimestampSpec("timestamp", "posix", null))
      .buildBeam()
  }
}