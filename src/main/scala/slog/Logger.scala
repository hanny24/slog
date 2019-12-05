package slog

import slog.macros.LoggerImpl

class Logger[F[_]](val underlying: SimpleLogger[F]) {
  import scala.language.experimental.macros

  def debug: LogBuilder[F] = macro LoggerImpl.debugImpl[F]
  def error: LogBuilder[F] = macro LoggerImpl.errorImpl[F]
  def info: LogBuilder[F] = macro LoggerImpl.infoImpl[F]
  def trace: LogBuilder[F] = macro LoggerImpl.traceImpl[F]
  def warn: LogBuilder[F] = macro LoggerImpl.warnImpl[F]
}
