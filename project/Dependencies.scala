import sbt._

object Dependencies {
  lazy val catsCore = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val catsEffect = "org.typelevel" %% "cats-effect" % "2.0.0"
  lazy val catsMtl = "org.typelevel" %% "cats-mtl-core" % "0.7.0"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val logstash = "net.logstash.logback" % "logstash-logback-encoder" % "6.3"
  lazy val magnolia = "com.propensive" %% "magnolia" % "0.12.3"
  lazy val monixDependency = "io.monix" %% "monix" % "3.1.0"
  lazy val slf4jDepedency = "org.slf4j" % "slf4j-api" % "1.7.29"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
}
