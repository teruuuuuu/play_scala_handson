name := """play_scala_slick3"""
organization := "jp.co.teruuu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  cache,
  filters,
  ws,
  "com.h2database"  %  "h2"      % "1.4.193",
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)