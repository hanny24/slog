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
    libraryDependencies += catsCore,
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val example = (project in file("example"))
  .settings(
    name := "example",
    libraryDependencies ++= Seq(catsCore, logback, logstash, monixDependency)
  )
  .dependsOn(root, generic, slf4j, monix)

lazy val generic = (project in file("generic"))
  .settings(
    name := "generic",
    libraryDependencies += magnolia
  )
  .dependsOn(core)

lazy val slf4j = (project in file("slf4j"))
  .settings(
    name := "slf4j",
    libraryDependencies ++= Seq(
      catsCore,
      catsEffect,
      catsMtl,
      logstash,
      slf4jDepedency
    )
  )
  .dependsOn(core, root)

lazy val monix = (project in file("monix"))
  .settings(
    name := "slf4j",
    libraryDependencies += monixDependency,
    libraryDependencies += catsMtl
  )
