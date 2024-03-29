package slog

trait LevelLogBuilder[F[_]] {
  def apply(msg: String): F[Unit]
  def apply(ex: Throwable, msg: String): F[Unit]
  def withArg[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
  def whenEnabled: WhenEnabledLogBuilder[F]
}
