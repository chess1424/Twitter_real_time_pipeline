package twitter.client

import java.util.Properties
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

/**
  * Created by carlosmartinez on 2/11/18.
  */

object KafkaUtil{
  def createProducer = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092") //kafka brokers
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "twitter.client.TweetSerializer")
    props.put("zk.connect", "localhost:2181")

    new KafkaProducer[String, Tweet](props)
  }

  //def createRecord(topic: String, key: String, value: String) = new ProducerRecord(topic, key, value)
}

object TwitterUtil{
  val twitterConfig = ConfigFactory.load()
  val consumerKey = twitterConfig.getString("twitter.consumer.key")
  val consumerSecret = twitterConfig.getString("twitter.consumer.secret")
  val tokenAccess = twitterConfig.getString("twitter.token.access")
  val tokenSecret = twitterConfig.getString("twitter.token.secret")

  val config = new twitter4j.conf.ConfigurationBuilder().
    setOAuthConsumerKey(consumerKey).
    setOAuthConsumerSecret(consumerSecret).
    setOAuthAccessToken(tokenAccess).
    setOAuthAccessTokenSecret(tokenSecret).build()

  def createListener(topic: String, key: String) =
    new  TwitterListener(KafkaUtil.createProducer, topic, key)
}
