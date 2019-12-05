package slog

trait StructureBuilder[T] {
  def long(value: Long): T
  def double(value: Double): T
  def string(value: String): T
  def structure(name: String, attributes: Map[String, T]): T
  def array(values: List[T]): T
}

object StructureBuilder {
  def apply[T](implicit ev: StructureBuilder[T]): StructureBuilder[T] = ev
}
