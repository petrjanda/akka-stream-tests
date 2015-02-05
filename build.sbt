import _root_.sbt.Keys._
import _root_.sbt.Resolver

name := "streams"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.5"

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.typesafeRepo("releases"),
  Resolver.mavenLocal
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-testkit" % "2.3.7",
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0-M2",
  "commons-net" % "commons-net" % "3.3",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)