package twitter

import java.io.Serializable

class Tweet extends Serializable {
  private var countryCode: String = null
  private var source: String = null
  private var userId: java.lang.Long = null
  private var isRetweet: java.lang.Boolean = null;
  private var text: String = null

  def this(countryCode: String, source: String, userId: Long, isRetweet: Boolean, text: String){
    this()
    this.countryCode = countryCode
    this.source = source
    this.userId = userId
    this.isRetweet = isRetweet
    this.text = text
  }

  def getCountryCode: String = this.countryCode
  def getSource: String = this.source
  def getUserId: Long = this.userId
  def getIsRetweet: Boolean = this.isRetweet
  def getText: String = this.text

  override def toString: String = super.toString
}