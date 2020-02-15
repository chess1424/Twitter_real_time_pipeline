name := "Tweet_serializer_deserializer"

version := "1.0"

scalaVersion := "2.11.0"

resolvers ++= Seq(
  "MavenRepository" at "https://mvnrepository.com/repos/central",
  "bintray-sbt-plugins" at "https://dl.bintray.com/eed3si9n/sbt-plugins/"
)

val sparkVersion = "1.5.2"

libraryDependencies ++= Seq(
   "org.apache.kafka" %% "kafka" % "0.10.1.0",
  "org.codehaus.jackson" % "jackson-mapper-asl" % "1.9.2"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

