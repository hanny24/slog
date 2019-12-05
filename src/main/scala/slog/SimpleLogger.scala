package slog

trait SimpleLogger[F[_]] {
  def debug: LogBuilder[F]
  def error: LogBuilder[F]
  def info: LogBuilder[F]
  def trace: LogBuilder[F]
  def warn: LogBuilder[F]
}
