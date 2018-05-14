import sbt._

object Dependencies {
  lazy val specs2 = "org.specs2" %% "specs2-core" % "4.2.0" % "test"
  lazy val specs2junit = "org.specs2" %% "specs2-junit" % "4.2.0" % "test"
  lazy val sttp = "com.softwaremill.sttp" %% "core" % "1.1.13"

 val backendDeps =
   Seq(specs2, specs2junit, sttp)
}
