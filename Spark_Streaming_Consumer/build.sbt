name := "Spark_Streaming_Consumer"

version := "0.1"

scalaVersion := "2.11.0"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming" % "1.6.2",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.2",
  "org.apache.spark" %% "spark-sql" % "1.6.2",
  "org.apache.kafka" %% "kafka" % "0.10.1.0",
  "io.druid" %% "tranquility-spark" % "0.8.3" excludeAll(
    ExclusionRule(organization = "com.sun.jersey")
  )
)
