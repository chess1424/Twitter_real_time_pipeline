package twitter.client

import twitter4j._

/**
  * Created by carlosmartinez on 2/11/18.
  */

//Object to filter tweets in real time by specific word/words
object SearchStreamer {
  def main(args: Array[String]) {

    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(args))
    Thread.sleep(10000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}





