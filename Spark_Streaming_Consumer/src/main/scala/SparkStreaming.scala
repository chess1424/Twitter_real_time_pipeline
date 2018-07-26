import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.{SparkConf, SparkContext}
import java.util.Date
import java.text.SimpleDateFormat


object SparkStreaming {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("SparkStreamingConsumer").setMaster("local[2]")
    val spark = new SparkContext(sparkConf)
    spark.setLogLevel("ERROR")

    val streamingContext = new StreamingContext(spark, Seconds(10))
    val df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")


    val lines = KafkaUtils.createStream(streamingContext
      , "localhost:2181"
      , "twitter"
      , Map("twitter-streamer" ->
        4))


//    lines.foreachRDD( rdd =>
//      rdd.collect().foreach(println)
//

    lines.foreachRDD { (rdd, time) =>
      val date = df.format(time.milliseconds)
      println("=============================")
      println("Dstream for time " + date)
      println("=============================")


      val totalTweets = rdd.collect.length
      println("Number of Tweets " + totalTweets)

      val totalWords = rdd.flatMap(tweet => tweet._2.split(" ")).map(word => (word, 1)).map(tuple => tuple._2).sum()
      println("Total words " + totalWords.toInt)

      println("Average number of words by tweet " + totalWords / totalTweets)

      val trumpOccurrences = rdd.flatMap(tweet => tweet._2.split( " ").filter(word => word == "trump" || word == "TRUMP")).
        map(trumpWord => (trumpWord, 1)).map(tuple => tuple._2).sum()
      println("Trump word occurrences " + trumpOccurrences)


      val ooyalaOccurrences = rdd.flatMap(tweet => tweet._2.split( " ").filter(word => word == "ooyala" || word == "OOYALA" || word ==  "Ooyala")).
        map(ooyalaWord => (ooyalaWord, 1)).map(tuple => tuple._2).sum()

      println("Ooyala word occurrences " + ooyalaOccurrences)

      val ooyalaTweets = rdd.filter(rdd => rdd._2.contains("Oooyala") || rdd._2.contains("ooyala") || rdd._2.contains("OOYALA"))
      println("Ooyala Tweets " + ooyalaTweets.count())


      ooyalaTweets.foreach(println)


    }

    //lines.map(x => (x._2, 1)).reduceByKey(_ + _).print()

    streamingContext.start
    streamingContext.awaitTermination
  }

}
