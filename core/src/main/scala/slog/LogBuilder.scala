package slog

trait LogBuilder[F[_]] {
  def msg(msg: String): F[Unit]
  def msg(ex: Throwable)(msg: String): F[Unit]
  def context[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
}
