
name := "adventofcode"

version := "0.1"

scalaVersion := "2.12.7"

lazy val `advent-challenge` = project

lazy val `advent` = Project("advent", file("."))
  .aggregate(`advent-challenge`)