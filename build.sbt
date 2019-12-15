import Dependencies._

ThisBuild / scalaVersion := "2.13.1"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "slog"
  )
  .aggregate(api, core, example, generic, monix, slf4j)

lazy val api = (project in file("api"))
  .settings(
    name := "slog-api"
  )
  .dependsOn(core)

lazy val core = (project in file("core"))
  .settings(
    name := "slog-core",
    libraryDependencies += catsCore,
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val example = (project in file("example"))
  .settings(
    name := "slog-example",
    libraryDependencies ++= Seq(catsCore, logback, logstash, monixDependency)
  )
  .dependsOn(api, generic, slf4j, monix)

lazy val generic = (project in file("generic"))
  .settings(
    name := "slog-generic",
    libraryDependencies += magnolia
  )
  .dependsOn(core)

lazy val monix = (project in file("monix"))
  .settings(
    name := "slog-slf4j",
    libraryDependencies ++= Seq(catsMtl, monixDependency)
  )

lazy val slf4j = (project in file("slf4j"))
  .settings(
    name := "slog-slf4j",
    libraryDependencies ++= Seq(
      catsCore,
      catsEffect,
      catsMtl,
      logstash,
      slf4jDepedency
    ),
    libraryDependencies ++= Seq(
      diffx % Test,
      scalaTest % Test
    )
  )
  .dependsOn(api, core)
