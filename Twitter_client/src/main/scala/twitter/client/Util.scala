package twitter.client

import twitter4j._
import com.typesafe.config.ConfigFactory

/**
  * Created by carlosmartinez on 2/11/18.
  */


object TwitterConfig{
  val twitterConfig = ConfigFactory.load()

  def consumerKey = twitterConfig.getString("twitter.consumer.key")
  def consumerSecret = twitterConfig.getString("twitter.consumer.secret")
  def tokenAccess = twitterConfig.getString("twitter.token.access")
  def tokenSecret = twitterConfig.getString("twitter.token.secret")
}

object Util {
  val config = new twitter4j.conf.ConfigurationBuilder().
    setOAuthConsumerKey(TwitterConfig.consumerKey).
    setOAuthConsumerSecret(TwitterConfig.consumerSecret).
    setOAuthAccessToken(TwitterConfig.tokenAccess).
    setOAuthAccessTokenSecret(TwitterConfig.tokenSecret).build()

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) { println(status.getText) }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }
}
