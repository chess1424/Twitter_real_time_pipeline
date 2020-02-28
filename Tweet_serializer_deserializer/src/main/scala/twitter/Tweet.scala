package twitter

import java.io.Serializable

class Tweet extends Serializable{
  private var userId: String = null
  private var countryCode: String = null
  private var source: String = null
  private var attachedLinks: String = null
  private var hashTags: String = null
  private var words: Int = 0
  private var isVerified: Boolean= false
  private var isRetweet: Boolean = false
  private var isPossibleSensitive: Boolean = false
  private var timesRetweeted: Long = 0
  private var followers: Int = 0
  private var friends: Int = 0

  def this(userId: String, countryCode: String, source: String, attachedLinks: String, hashTags: String,
           words: Int, isVerified: Boolean, isRetweet: Boolean, isPossibleSensitive: Boolean, timesRetweeted: Long,
           followers: Int, friends: Int){
    this()
    this userId = userId
    this countryCode = countryCode
    this source = source
    this attachedLinks = attachedLinks
    this hashTags = hashTags
    this words = words
    this isVerified = isVerified
    this isRetweet = isRetweet
    this isPossibleSensitive = isPossibleSensitive
    this timesRetweeted = timesRetweeted
    this followers = followers
    this friends = friends
  }

  def getUserId: String = this.userId
  def getCountryCode: String = this.countryCode
  def getSource: String = this.source
  def getAttachedLinks: String = this.attachedLinks
  def getHashTags: String = this.hashTags
  def getWords: Int = this.words
  def getIsVerified: Boolean = this.isVerified
  def getIsRetweet: Boolean = this.isRetweet
  def getIsPossibleSensitive: Boolean = this.isPossibleSensitive
  def getTimesRetweeted: Long = this.timesRetweeted
  def getFollowers: Int = this.followers
  def getFriends: Int = this.friends

  override def toString: String =
    s"""userId = $userId
       | countryCode = $countryCode
       | source =  $source
       | attachedLinks = $attachedLinks
       | hashTags = $hashTags
       | words = $words
       | isVerified = $isVerified
       | isRetweet = $isRetweet
       | isPossibleSensitive = $isPossibleSensitive
       | timesRetweeted = $timesRetweeted
       | followers = $followers
       | friends = $friends
     """.stripMargin
}