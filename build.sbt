name := "cw-three"

version := "1.0"

scalaVersion := "2.11.8"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.2"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.2"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

libraryDependencies += "org.scalatest" % "scalatest_2.12.0-M4" % "3.0.0-M16-SNAP3"
    