package twitter.client

import org.apache.kafka.clients.producer.KafkaProducer
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}

class TwitterListener(producer: KafkaProducer[String, String], topic: String, key: String) {

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
      System.out.println("Recording: " + status.getText)

      val record = KafkaUtil.createRecord(topic, key, status.getText)
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
