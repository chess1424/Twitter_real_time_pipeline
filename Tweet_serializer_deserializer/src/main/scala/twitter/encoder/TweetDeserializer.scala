package twitter.encoder

import java.util
import org.apache.kafka.common.serialization.Deserializer
import org.codehaus.jackson.map.ObjectMapper

class TweetDeserializer extends Deserializer[Tweet]{
  override def configure(map: util.Map[String, _], b: Boolean): Unit = {
  }
  override def deserialize(s: String, bytes: Array[Byte]): Tweet = {
    val mapper = new ObjectMapper()
    val user = mapper.readValue(bytes, classOf[Tweet])
    user
  }
  override def close(): Unit = {
  }
}
