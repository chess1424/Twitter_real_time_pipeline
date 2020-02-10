package twitter.client

class Tweet {
  private var countryCode: String = null
  private var source: String = null
  private var userId: java.lang.Long = null
  private var isRetweet: java.lang.Boolean = null;
  private var text: String = null

  def this(countryCode: String, source: String, userId: Long, isRetweet: Boolean, text: String){
    this();
    this.countryCode = countryCode
    this.isRetweet = isRetweet
    this.userId = userId
    this.text = text
  }

  def getCountryCode: String = this.countryCode
  def getSource: String = this.source
  def getUserId: Long = this.userId
  def getIsRetweet: Boolean = this.isRetweet
  def getText: String = this.text
}
