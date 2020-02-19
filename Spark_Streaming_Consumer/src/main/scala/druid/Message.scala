package druid

import com.fasterxml.jackson.annotation.JsonValue
import com.github.nscala_time.time.Imports._
import com.metamx.common.scala.untyped._
import com.metamx.tranquility.typeclass.Timestamper

case class Message(time: DateTime, countryCode: String,  source: String, userId: Long,
                   isRetweet: Boolean, text: String) {

  @JsonValue
  def toMap: Map[String, Any] = Map(
    "timestamp" -> (time.getMillis / 1000),
    "countryCode" -> countryCode,
    "source" -> source,
    "userId" -> userId,
    "isRetweet" -> isRetweet,
    "text" -> text
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
      str(d("countryCode")),
      str(d("source")),
      int(d("userId")),
      bool(d("isRetweet")),
      str(d("text"))
    )
  }
}