package twitter

import java.io.Serializable

class Tweet(val userId: String, val countryCode: String, val source: String, val attachedLinks: List[String],
            val hashTags: List[String], val words: Int, val isVerified: Boolean, val isRetweet: Boolean,
            val isPossibleSensitive: Boolean, val timesRetweeted: Long, val followers: Int, val friends: Int)
  extends Serializable