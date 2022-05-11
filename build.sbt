ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "meet-and-tech",
    idePackagePrefix := Some("com.leonteq.meetandtech"),

    libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.11"
  )
