name := "Twitter_client"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "org.twitter4j" % "twitter4j-core" % "4.0.1",
  "org.twitter4j" % "twitter4j-stream" % "4.0.1",
  "com.typesafe" % "config" % "1.2.0",
  "org.apache.kafka" % "kafka-clients" % "1.0.0"
)
    