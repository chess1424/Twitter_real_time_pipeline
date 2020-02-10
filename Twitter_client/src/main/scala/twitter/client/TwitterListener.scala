package twitter.client

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener}

class TwitterListener(producer: KafkaProducer[String, Tweet], topic: String, key: String) {

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {
//      if(status.getPlace != null && status.getPlace.getCountryCode != null)
//        System.out.println(status.getPlace.getCountryCode)

//      System.out.println(status.getSource)
//      System.out.println(status.getUser.getId)
//      System.out.println(status.isRetweet)
//      System.out.println(status.getText)

      var tweet  = new Tweet("MX", status.getSource, status.getUser.getId,
        status.isRetweet, status.getText)

      System.out.println(tweet.toString);


      System.out.println("==================================")
      //System.out.println("Recording: " + status.getText)
      //val record = KafkaUtil.createRecord(topic, key, status.getText)

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
