package twitter.client

import twitter4j._


/**
  * Created by carlosmartinez on 2/11/18.
  */

//Object to filter tweets in real time by specific word/words
object SearchStreamer {
  def main(args: Array[String]) {
    val twitterListener = TwitterUtil.createListener("twitter-streamer", "TRUMP")
    val twitterStream = new TwitterStreamFactory(TwitterUtil.config).getInstance

    twitterStream.addListener(twitterListener.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(Array("AMLO amlo Amlo")))


    while(true){//hack to maintain connection
    }

    twitterStream.cleanUp
    twitterStream.shutdown
  }
}





