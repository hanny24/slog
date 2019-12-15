package slog

trait StructureEncoder[T] {
  def encode[O](value: T)(implicit structureBuilder: StructureBuilder[O]): O
}

object StructureEncoder {
  def apply[T](implicit ev: StructureEncoder[T]): StructureEncoder[T] = ev

  implicit val intEncoder: StructureEncoder[Int] =
    new StructureEncoder[Int] {
      override def encode[O](value: Int)(
          implicit structureBuilder: StructureBuilder[O]
      ): O = structureBuilder.long(value)
    }

  implicit val longEncoder: StructureEncoder[Long] =
    new StructureEncoder[Long] {
      override def encode[O](value: Long)(
          implicit structureBuilder: StructureBuilder[O]
      ): O = structureBuilder.long(value)
    }

  implicit val doubleEncoder: StructureEncoder[Double] =
    new StructureEncoder[Double] {
      override def encode[O](value: Double)(
          implicit structureBuilder: StructureBuilder[O]
      ): O = structureBuilder.double(value)
    }

  implicit val stringEncoder: StructureEncoder[String] =
    new StructureEncoder[String] {
      override def encode[O](value: String)(
          implicit structureBuilder: StructureBuilder[O]
      ): O = structureBuilder.string(value)
    }

  implicit def listEncoder[T: StructureEncoder]: StructureEncoder[List[T]] =
    new StructureEncoder[List[T]] {
      override def encode[O](
          value: List[T]
      )(implicit structureBuilder: StructureBuilder[O]): O = {
        structureBuilder.array(value.map(StructureEncoder[T].encode[O]))
      }
    }
}
