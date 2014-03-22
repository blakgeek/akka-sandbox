name := "scala"

version := "1.0"

scalaVersion := "2.10.3"

resolvers += "typesafe" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.0",
  "com.typesafe.akka" %% "akka-cluster" % "2.3.0"
)