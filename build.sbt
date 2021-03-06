name := """contacts"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "commons-io" % "commons-io" % "2.4",
  "org.mongodb" % "mongo-java-driver" % "3.1.0",
  "org.mongodb.morphia" % "morphia" % "1.0.1",
  javaJdbc,
  cache,
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := StaticRoutesGenerator


fork in run := false