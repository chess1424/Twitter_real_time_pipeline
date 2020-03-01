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
      val tweetId = status.getId.toString

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
      val userVerifiable = if(status.getUser.isVerified) "Verified" else "Not verified"
      val retweeetStatus = if(status.isRetweet) "Retweeted" else "Not retweeted"
      val sensitivity = if(status.isPossiblySensitive) "Sensitive" else "Not sensitive"

      //Building Metrics
      val words = status.getText.length
      val timesRetweeted = status.getRetweetCount
      val followers = status.getUser.getFollowersCount
      val friends = status.getUser.getFriendsCount

      val tweet  = new Tweet(userId, tweetId, countryCode, source, attachedLinks.mkString(", "), hashtags.mkString(", "),
        userVerifiable, retweeetStatus, sensitivity, words, timesRetweeted, followers, friends)

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
