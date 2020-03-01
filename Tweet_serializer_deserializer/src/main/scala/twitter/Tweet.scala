package twitter

import java.io.Serializable

class Tweet extends Serializable{
  private var userId: String = null
  private var tweetId: String = null
  private var countryCode: String = null
  private var source: String = null
  private var attachedLinks: String = null
  private var hashTags: String = null
  private var userVerifiable: String = null //verified, not verified
  private var retweetStatus: String = null //retweeted, not retweeted
  private var sensitivity: String = null //sensible, not sensible
  private var words: Int = 0
  private var timesRetweeted: Long = 0
  private var followers: Int = 0
  private var friends: Int = 0

  def this(userId: String, tweetId: String, countryCode: String, source: String, attachedLinks: String, hashTags: String,
           userVerifiable: String, retweetStatus: String, sensitivity: String, words: Int, timesRetweeted: Long, followers: Int,
           friends: Int){
    this()
    this userId = userId
    this tweetId = tweetId
    this countryCode = countryCode
    this source = source
    this attachedLinks = attachedLinks
    this hashTags = hashTags
    this userVerifiable = userVerifiable
    this retweetStatus = retweetStatus
    this sensitivity = sensitivity
    this words = words
    this timesRetweeted = timesRetweeted
    this followers = followers
    this friends = friends
  }

  def getUserId: String = this.userId
  def getTweetId: String = this.tweetId
  def getCountryCode: String = this.countryCode
  def getSource: String = this.source
  def getAttachedLinks: String = this.attachedLinks
  def getHashTags: String = this.hashTags
  def getUserVerifiable: String = this.userVerifiable
  def getRetweetStatus: String = this.retweetStatus
  def getSensitivity: String = this.sensitivity
  def getWords: Int = this.words
  def getTimesRetweeted: Long = this.timesRetweeted
  def getFollowers: Int = this.followers
  def getFriends: Int = this.friends

  override def toString: String =
    s"""
       | userId = $userId
       | tweetId = $tweetId
       | countryCode = $countryCode
       | source =  $source
       | attachedLinks = $attachedLinks
       | hashTags = $hashTags
       | userVerifiable = $userVerifiable
       | retweetStatus = $retweetStatus
       | sensitivity = $sensitivity
       | words = $words
       | timesRetweeted = $timesRetweeted
       | followers = $followers
       | friends = $friends
     """.stripMargin
}