name := "akka_http"

version := "1.0"

scalaVersion := "2.10.4"


libraryDependencies ++= Seq(
				"com.typesafe.akka" % "akka-http-core-experimental_2.10" % "1.0",
				"com.typesafe.akka" % "akka-stream-experimental_2.10" % "1.0",
				"com.typesafe.akka" % "akka-http-experimental_2.10" % "1.0"
				       )

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Scalaz Bintray Repo" at "https://dl.bintray.com/scalaz/releases"
