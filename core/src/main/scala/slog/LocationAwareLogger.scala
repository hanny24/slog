package slog

trait LocationAwareLogger[F[_]] {
  def debug(filename: String, line: Int): LevelLogBuilder[F]
  def error(filename: String, line: Int): LevelLogBuilder[F]
  def info(filename: String, line: Int): LevelLogBuilder[F]
  def trace(filename: String, line: Int): LevelLogBuilder[F]
  def warn(filename: String, line: Int): LevelLogBuilder[F]
}
