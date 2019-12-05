import Dependencies._

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "slog",

    libraryDependencies += scalaTest % Test,
  ).dependsOn(core)

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  )

lazy val example = (project in file("example"))
  .settings(
    name := "example",
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
  ).dependsOn(root, generic)

lazy val generic = (project in file("generic"))
  .settings(
    name := "generic",
    libraryDependencies += "com.propensive" %% "magnolia" % "0.12.3"
  ).dependsOn(core)