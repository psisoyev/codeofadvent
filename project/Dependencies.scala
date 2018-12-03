import sbt._

object Dependencies {
  private val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http" % Version.akkaHttp,
    "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp % Test
  )

  private val akkaStream = Seq(
    "com.typesafe.akka" %% "akka-stream" % Version.akkaStream,
    "com.typesafe.akka" %% "akka-stream-testkit" % Version.akkaStream % Test
  )
  
  private val circeAll = Seq("circe-core", "circe-generic", "circe-parser", "circe-java8").map("io.circe" %% _ % Version.circe)
  private val akkaHttpCirce =  "de.heikoseeberger" %% "akka-http-circe" % Version.akkaHttpCirce

  private val logback = "ch.qos.logback" % "logback-classic" % Version.logback
  private val logstash = "net.logstash.logback" % "logstash-logback-encoder" % Version.logstash
  private val slf4s = "org.slf4s" %% "slf4s-api" % Version.slf4s

  val CommonDependencies = akkaHttp ++ akkaStream ++ circeAll ++ Seq(logback, logstash, slf4s, akkaHttpCirce)

  object Version {
    val akkaHttp = "10.1.5"
    val akkaStream = "2.5.18"
    val circe = "0.9.3"
    val logback = "1.2.3"
    val logstash = "4.11"
    val slf4s = "1.7.25"
    val akkaHttpCirce = "1.22.0"
  }
}


