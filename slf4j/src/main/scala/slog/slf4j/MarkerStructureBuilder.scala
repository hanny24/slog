package slog.slf4j

import slog.StructureBuilder

import scala.jdk.CollectionConverters._

private[slf4j] object MarkerStructureBuilder {
  implicit val markerStructureBuilder: StructureBuilder[Any] =
    new StructureBuilder[Any] {
      override def long(value: Long): Any = value

      override def double(value: Double): Any = value

      override def string(value: String): Any = value

      override def structure(name: String, attributes: Map[String, Any]): Any =
        attributes.asJava

      override def array(values: List[Any]): Any = values.asJava
    }
}
