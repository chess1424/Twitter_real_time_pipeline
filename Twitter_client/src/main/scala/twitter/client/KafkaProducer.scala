package twitter.client

import twitter4j._

/**
  * Created by carlosmartinez on 2/11/18.
  */

//Object to filter tweets in real time by specific word/words
object SearchStreamer {
  def main(args: Array[String]) {
    val twitterListener = TwitterUtil.createListener("twitter-streamer", "AMLO")
    val search = Array("AMLO")
    val twitterStream = new TwitterStreamFactory(TwitterUtil.config).getInstance

    twitterStream.addListener(twitterListener.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(search))
    Thread.sleep(10000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}





