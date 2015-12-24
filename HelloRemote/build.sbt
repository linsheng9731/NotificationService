name := "HelloRemote"

version := "1.0"

scalaVersion := "2.11.6"

resolvers ++= Seq(
"maven Repository" at "http://repo1.maven.org/maven2/",
"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
"repo.codahale.com" at "http://repo.codahale.com"
)


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.12",
  "com.typesafe.akka" %% "akka-remote" % "2.3.12"
 // "com.fasterxml.jackson.core" % "jackson-core" % "2.4.0",
 // "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.0"
//"com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.4.2"
)

libraryDependencies += "net.debasishg" % "redisclient_2.11" % "3.0"

libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.10" % "2.4.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.4.2"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.2"
