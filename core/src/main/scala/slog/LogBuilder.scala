package slog

trait LevelLogBuilder[F[_]] {
  def apply(msg: String): F[Unit]
  def apply(ex: Throwable)(msg: String): F[Unit]
  def withArg[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
  def whenEnabled[T](fv: LogBuilder[F] => F[Unit]): F[Unit]
}

trait LogBuilder[F[_]] {
  def log(msg: String): F[Unit]
  def log(ex: Throwable)(msg: String): F[Unit]
  def withArg[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
}
