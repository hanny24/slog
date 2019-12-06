package slog

trait LoggingContext[F[_]] {
  def use[T: StructureEncoder](key: String,
                               value: T): LoggingContext.LoggingBuilder[F]
}

object LoggingContext {
  def apply[F[_]](implicit ev: LoggingContext[F]): LoggingContext[F] = ev

  trait LoggingBuilder[F[_]] {
    def scoped[T](fv: F[T]): F[T]
    def use[T: StructureEncoder](key: String, value: T): LoggingBuilder[F]
  }
}
