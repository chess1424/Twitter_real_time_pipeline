name := "Tweet_serializer_deserializer"

version := "1.0"

scalaVersion := "2.11.0"

resolvers ++= Seq(
  "MavenRepository" at "https://mvnrepository.com/repos/central",
  "bintray-sbt-plugins" at "https://dl.bintray.com/eed3si9n/sbt-plugins/"
)

val sparkVersion = "1.5.2"

libraryDependencies ++= Seq(
   "org.apache.kafka" % "kafka-clients" % "1.0.0",
   "org.apache.spark" %% "spark-core" % sparkVersion
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

