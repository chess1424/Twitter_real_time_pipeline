package druid

import com.fasterxml.jackson.annotation.JsonValue
import com.github.nscala_time.time.Imports._
import com.metamx.common.scala.untyped._
import com.metamx.tranquility.typeclass.Timestamper



case class Message(time: DateTime, userId: String, countryCode: String, source: String, attachedLinks: String,
                   hashTags: String, words: Int, isVerified: Boolean, isRetweet: Boolean, isPossibleSensitive: Boolean,
                   timesRetweeted: Long, followers: Int, friends: Int){
  @JsonValue
  def toMap: Map[String, Any] = Map(
    "timestamp" -> (time.getMillis / 1000),
    "userId" -> userId,
    "countryCode" -> countryCode,
    "source" -> source,
    "attachedLinks" -> attachedLinks,
    "hashTags" -> hashTags,
    "words" -> words,
    "isVerified" -> isVerified,
    "isRetweet" -> isRetweet,
    "isPossibleSensitive" -> isPossibleSensitive,
    "timesRetweeted" -> timesRetweeted,
    "followers" -> followers,
    "friends" -> friends
  )
}

object Message {
  implicit val MyEventTimestamper = new Timestamper[Message] {
    def timestamp(a: Message) = a.time
  }

  val Columns = Seq("time", "oid",  "status")

  def fromMap(d: Dict): Message = {
    Message(
      new DateTime(long(d("timestamp")) * 1000),
      str(d("userId")),
      str(d("countryCode")),
      str(d("source")),
      str(d("attachedLinks")),
      str(d("hashTags")),
      int(d("words")),
      bool(d("isVerified")),
      bool(d("isRetweet")),
      bool(d("isPossibleSensitive")),
      long(d("timesRetweeted")),
      int(d("followers")),
      int(d("friends"))
    )
  }
}