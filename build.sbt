
name := "adventofcode"

version := "0.1"

scalaVersion := "2.12.7"

lazy val `advent-common` = project
  .settings(libraryDependencies ++= Dependencies.CommonDependencies)

lazy val `advent-challenge` = project
  .dependsOn(`advent-common`)

lazy val `advent` = Project("advent", file("."))
  .aggregate(
    `advent-common`,
    `advent-challenge`
  )