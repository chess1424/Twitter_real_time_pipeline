import sbt.{Project, ProjectRef, uri}

import sbt._
import Keys._

object GotoBuild {
  lazy val root = Project("root", sbt.file(".")).dependsOn(staminaCore)

  System.out.println("CARLOS CARLOS CALROS IS WORKING!!!!!!============")

  lazy val staminaCore = ProjectRef(uri("https://github.com/chess1424/Twitter_real_time_pipeline.git#master"), "Tweet_serializer_deserializer")
}