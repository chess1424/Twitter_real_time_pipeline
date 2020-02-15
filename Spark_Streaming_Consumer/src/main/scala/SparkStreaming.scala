import kafka.serializer.{StringDecoder}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}
import twitter.Tweet
import twitter.encoder.{TweetEncoder}

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

    val data: DStream[Tweet] =
    //al lines =
      KafkaUtils.createStream[String, Tweet, StringDecoder, TweetEncoder](
      streamingContext,
      kafkaParams,
      Map("twitter-streamer" -> 4),
      StorageLevel.MEMORY_AND_DISK_SER_2
    ).map(_._2)


    data.foreachRDD(rdd => rdd.foreach(tweet => {
      System.out.println("============================")
      System.out.println(tweet.getCountryCode)
      System.out.println(tweet.getText)
    }))

    streamingContext.start
    streamingContext.awaitTermination
  }
}
