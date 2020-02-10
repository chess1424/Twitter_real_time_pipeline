package twitter.client

import java.util
import org.apache.kafka.common.serialization.Serializer
import org.codehaus.jackson.map.ObjectMapper

class TweetSerializer extends Serializer[Tweet]{
  override def configure(map: util.Map[String, _], b: Boolean): Unit = {
  }
  override def serialize(s: String, t: Tweet): Array[Byte] = {
    if(t==null)
      null
    else
    {
      val objectMapper = new ObjectMapper()
      objectMapper.writeValueAsString(t).getBytes
    }
  }
  override def close(): Unit = {
  }
}
