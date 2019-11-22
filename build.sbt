name := """sbt-sangriaschemagen"""
organization := "gov.nationalarchives"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.8"
sbtPlugin := true

// choose a test framework

// utest
//libraryDependencies += "com.lihaoyi" %% "utest" % "0.4.8" % "test"
//testFrameworks += new TestFramework("utest.runner.Framework")

// ScalaTest
//libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1" % "test"
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

// Specs2
//libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.9.1" % "test")
//scalacOptions in Test ++= Seq("-Yrangepos")

//bintrayPackageLabels := Seq("sbt","plugin")
//bintrayVcsUrl := Some("""git@github.com:gov.nationalarchives/sbt-sangriaschemagen.git""")

publishTo := Some(Resolver.file("local-ivy", file("/home/sam/.ivy2/cache")))

initialCommands in console := """import gov.nationalarchives.sbt._"""

enablePlugins(ScriptedPlugin)

// set up 'scripted; sbt plugin for testing sbt plugins
scriptedLaunchOpts ++=
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)

libraryDependencies += "com.eed3si9n" %% "treehugger" % "0.4.3"
libraryDependencies += "com.graphql-java" % "graphql-java" % "13.0"

resolvers += Resolver.sonatypeRepo("public")
resolvers += Resolver.mavenCentral