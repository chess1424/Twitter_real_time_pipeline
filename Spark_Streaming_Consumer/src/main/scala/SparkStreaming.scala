import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}

object SparkStreaming {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("SparkStreamingConsumer").setMaster("local[2]")
    val spark = new SparkContext(sparkConf)

    val streamingContext = new StreamingContext(spark, Seconds(1))

    val lines = KafkaUtils.createStream(streamingContext
      , "localhost:2181"
      , "test-group"
      , Map("twitter-streamer" -> 4))

    lines.print()

    streamingContext.start
    streamingContext.awaitTermination
  }
}
