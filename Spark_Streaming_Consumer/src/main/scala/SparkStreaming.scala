import druid.{Message, MessageBeamFactory}
import kafka.serializer.StringDecoder
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}
import org.joda.time.DateTime
import twitter.Tweet
import twitter.encoder.TweetEncoder
import com.metamx.tranquility.spark.BeamRDD._



object SparkStreaming {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkStreamingConsumer").setMaster("local[2]")
    val spark = new SparkContext(sparkConf)

    val streamingContext = new StreamingContext(spark, Seconds(1))

    val kafkaParams = Map[String, String](
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "test-group"
      //"zookeeper.connection.timeout.ms" -> "10000",
      //"auto.offset.reset" -> "smallest"
    )


    val t =
    //al lines =
      KafkaUtils.createStream[String, Tweet, StringDecoder, TweetEncoder](
      streamingContext,
      kafkaParams,
      Map("twitter-streamer" -> 4),
      StorageLevel.MEMORY_AND_DISK_SER_2
    ).map(tweet => tweet._2).map(tweet => Message(new DateTime(), tweet.getCountryCode, tweet.getSource, tweet.getUserId,
        tweet.getIsRetweet, tweet.getText))


    t.foreachRDD(rdd => rdd.propagate(new MessageBeamFactory))

    streamingContext.start
    streamingContext.awaitTermination
  }
}
