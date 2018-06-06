name := "Spark_Streaming_Consumer"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.2",
  "org.apache.spark" %% "spark-streaming" % "1.6.2",
  "org.apache.spark" %% "spark-streaming-kafka" % "1.6.2",
  "org.apache.spark" %% "spark-sql" % "1.6.2"
)
