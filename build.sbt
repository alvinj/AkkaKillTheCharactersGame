import AssemblyKeys._

// sbt-assembly
assemblySettings

name := "AkkaKillTheCharactersGame"

version := "1.0"

scalaVersion := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.1.1", 
  "org.scala-lang" % "scala-swing" % "2.10+",
  "commons-cli" % "commons-cli" % "1.2"
)

