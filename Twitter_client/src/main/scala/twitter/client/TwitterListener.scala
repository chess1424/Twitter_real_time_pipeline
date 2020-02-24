package twitter.client

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}
import twitter.Tweet
import scala.util.matching.Regex

class TwitterListener(producer: KafkaProducer[String, Tweet], topic: String, key: String) {

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      var countryCode:String = "Unknown"
      if(status.getPlace != null && status.getPlace.getCountryCode != null)
        countryCode = status.getPlace.getCountryCode

      val keyValPattern: Regex =  """(?<=nofollow\\">)(.*)(?=</a)""".r
      val source = keyValPattern.findFirstIn(status.getSource).orElse(Option("Unknown")).get

      val tweet  = new Tweet(countryCode, source, status.getUser.getId,
        status.isRetweet, status.getText)

      val record = new ProducerRecord[String, Tweet](topic,key,tweet)
      println("Sending the record " + record)
      producer.send(record)
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) {
      producer.close()
      ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }
}
