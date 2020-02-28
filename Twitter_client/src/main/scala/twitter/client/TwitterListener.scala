package twitter.client

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j._
import twitter.Tweet

import scala.util.matching.Regex

object Vars {
  val keyValPattern: Regex =  """(?<=nofollow">)(.*)(?=</a)""".r
  val UNKNOWN = "unknown"
}

class TwitterListener(producer: KafkaProducer[String, Tweet], topic: String, key: String) {

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) {

      //Building Dimensions
      val userId = status.getUser.getId.toString

      val countryCode = {
        try {
          Some(status.getPlace.getCountryCode)
        } catch {
          case _ => None
        }
      }.getOrElse(Vars.UNKNOWN)

      val source = Vars.keyValPattern.findFirstIn(status.getSource).getOrElse(Vars.UNKNOWN)

      var attachedLinks: List[String] = List()
      var hashtags: List[String] = List()
      status.getURLEntities.foreach(url => {attachedLinks = url.getURL :: attachedLinks})
      status.getHashtagEntities.foreach(hashTag => {hashtags = hashTag.getText :: hashtags})

      //Building Metrics
      val words = status.getText.length
      val isVerified = status.getUser.isVerified
      val isRetweet = status.isRetweet
      val isPossibleSensitive = status.isPossiblySensitive
      val timesRetweeted = status.getRetweetCount
      val followers = status.getUser.getFollowersCount
      val friens = status.getUser.getFriendsCount

//      println("======Dimensions=====")
//      println("userId  = " + userId)
//      println("countryCode = " + countryCode)
//      println("source = " + source)
//      println("attachedLinks = " + attachedLinks)
//      println("hashtags = " + hashtags)
//
//      println("======Metrics=====")
//      println("words = " + words)
//      println("isVerified = " + isVerified)
//      println("isRetweet = " + isRetweet)
//      println("isPossibleSensitive = " + isPossibleSensitive)
//      println("timesRetweeted = " + timesRetweeted)
//      println("followers = " + followers)
//      println("freinds = " + friens)

      val tweet  = new Tweet(userId, countryCode, source, attachedLinks.mkString(", "), hashtags.mkString(", "), words,
        isVerified, isRetweet, isPossibleSensitive, timesRetweeted, followers, friens)

      val record = new ProducerRecord[String, Tweet](topic,key,tweet)
      println("====Sending the record:=== \n " + record)
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
