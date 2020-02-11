package twitter.client

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}
import twitter.encoder.Tweet

class TwitterListener(producer: KafkaProducer[String, Tweet], topic: String, key: String) {

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      var countryCode:String = null
      if(status.getPlace != null && status.getPlace.getCountryCode != null)
        countryCode = status.getPlace.getCountryCode

      var tweet  = new Tweet(countryCode, status.getSource, status.getUser.getId,
        status.isRetweet, status.getText)

      val record = new ProducerRecord[String, Tweet](topic,key,tweet)
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
