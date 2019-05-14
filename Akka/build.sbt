name := "library-system"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.5.22",
  "com.typesafe.akka" %% "akka-remote" % "2.5.22",
  "com.typesafe.akka" %% "akka-stream" % "2.5.22"
)
