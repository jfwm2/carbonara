val VERSION = "0.0.1"

lazy val commonSettings = Seq(
  organization := "com.criteo.carbonara",
  version := VERSION,
  scalaVersion := "2.12.8",
  crossScalaVersions := Seq(scalaVersion.value),
  scalacOptions ++= {
    Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xlint",
      "-Xlint:-inaccessible",
      "-Yno-adapted-args",
      "-Ypartial-unification",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Xfuture",
      "-Ywarn-unused-import",
      "-Ywarn-macros:after"
    )
  },

  // Tests
  fork in Test := true,
)

val versions = new {
  val prometheus = "0.6.0"
}

lazy val prometheus = Seq(
  libraryDependencies ++= Seq(
    "simpleclient",
    "simpleclient_hotspot", // JVM metrics
    "simpleclient_httpserver" // HTTP server to expose metrics
  ).map("io.prometheus" % _ % versions.prometheus)
)

lazy val relay = (project in file("relay"))
  .settings(
    commonSettings,
    prometheus,
    mainClass in(Compile, run) := Some("carbonara.relay.Run")
  )

lazy val root = (project in file("."))
  .settings(
    commonSettings,
    publishArtifact := false,
  )
  .aggregate(relay)
