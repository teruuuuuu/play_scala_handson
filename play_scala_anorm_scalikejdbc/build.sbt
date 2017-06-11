name := """play_scala_anorm_scalikejdbc"""
organization := "jp.co.teruuu"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  cache,
  evolutions,
  filters,
  jdbc,
  ws,
  "com.h2database"  %  "h2"      % "1.4.193",
  "com.typesafe.play" %% "anorm" % "2.4.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1"
)