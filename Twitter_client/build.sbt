name := "Twitter_client"

version := "1.0"

scalaVersion := "2.11.0"

resolvers  += "MavenRepository" at "https://mvnrepository.com/repos/central"

val sparkVersion = "1.5.2"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.0",
  "org.apache.kafka" % "kafka-clients" % "1.0.0",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.3",
  "org.apache.spark" %% "spark-streaming-twitter" % "1.5.2",
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

