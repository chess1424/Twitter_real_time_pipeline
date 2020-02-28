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

//    val lines =
//      KafkaUtils.createStream[String, Tweet, StringDecoder, TweetEncoder](
//        streamingContext,
//        kafkaParams,
//        Map("twitter-streamer" -> 4),
//        StorageLevel.MEMORY_AND_DISK_SER_2
//      )map(tweet => tweet._2)
//
//    lines.foreachRDD(rdd => rdd.foreach(t => println(t.toString())))

    val t =
    //al lines =
      KafkaUtils.createStream[String, Tweet, StringDecoder, TweetEncoder](
      streamingContext,
      kafkaParams,
      Map("twitter-streamer" -> 4),
      StorageLevel.MEMORY_AND_DISK_SER_2
    ).map(tweet => tweet._2).map(tweet =>
        {
          val time = new DateTime()
          System.out.println("Received " + tweet + " with time " + time);
          Message(time, tweet.getUserId, tweet.getCountryCode, tweet.getSource, tweet.getAttachedLinks,
            tweet.getHashTags, tweet.getWords, tweet.getIsVerified, tweet.getIsRetweet, tweet.getIsPossibleSensitive,
            tweet.getTimesRetweeted, tweet.getFollowers, tweet.getFriends)
        })

    t.foreachRDD(rdd => rdd.propagate(new MessageBeamFactory))

    streamingContext.start
    streamingContext.awaitTermination
  }
}
