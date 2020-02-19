package twitter.encoder

import kafka.serializer.{Decoder, Encoder}
import kafka.utils.VerifiableProperties
import org.codehaus.jackson.map.ObjectMapper
import twitter.Tweet

class TweetEncoder(props: VerifiableProperties = null) extends Encoder[Tweet] with Decoder[Tweet] {
  val encoding =
    if(props == null)
      "UTF8"
    else
      props.getString("serializer.encoding", "UTF8")

  override def toBytes(s: Tweet): Array[Byte] =
    if(s == null)
      null
    else{
      val objectMapper = new ObjectMapper()
      objectMapper.writeValueAsString(s).getBytes
    }

  def fromBytes(bytes: Array[Byte]): Tweet = {
    val mapper = new ObjectMapper()
    val user = mapper.readValue(bytes, classOf[Tweet])
    user
  }
}
