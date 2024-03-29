import sbt._

object Dependencies {
  lazy val catsCore = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"
  lazy val catsMtl = "org.typelevel" %% "cats-mtl-core" % "0.7.0"
  lazy val diffx = "com.softwaremill.diffx" %% "diffx-scalatest" % "0.3.15"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val logstash = "net.logstash.logback" % "logstash-logback-encoder" % "6.3"
  lazy val magnolia = "com.propensive" %% "magnolia" % "0.12.5"
  lazy val monixDependency = "io.monix" %% "monix" % "3.1.0"
  lazy val scalaCollectionCompat = "org.scala-lang.modules" %% "scala-collection-compat" % "2.1.3"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.1.0"
  lazy val slf4jDepedency = "org.slf4j" % "slf4j-api" % "1.7.29"

}
