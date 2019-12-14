import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "slog",
    libraryDependencies += scalaTest % Test
  )
  .dependsOn(core)

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val example = (project in file("example"))
  .settings(
    name := "example",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3",
    libraryDependencies += "org.codehaus.groovy" % "groovy" % "2.5.8",
    libraryDependencies += "io.monix" % "monix_2.13" % "3.1.0",
  )
  .dependsOn(root, generic, slf4j)

lazy val generic = (project in file("generic"))
  .settings(
    name := "generic",
    libraryDependencies += "com.propensive" %% "magnolia" % "0.12.3"
  )
  .dependsOn(core)

lazy val slf4j = (project in file("slf4j"))
  .settings(
    name := "slf4j",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0",
    libraryDependencies += "org.typelevel" %% "cats-effect" % "2.0.0",
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.29",
    libraryDependencies += "org.typelevel" %% "cats-mtl-core" % "0.7.0",
    libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "6.3"
  )
  .dependsOn(core, root)
