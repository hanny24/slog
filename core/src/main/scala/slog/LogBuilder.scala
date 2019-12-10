package slog

import cats.FlatMap
import cats.syntax.flatMap._

trait LevelLogBuilder[F[_]] {
  def apply(msg: String): F[Unit]
  def apply(ex: Throwable)(msg: String): F[Unit]
  def withArg[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
  def whenEnabled: WhenEnabledLogBuilder[F]
}

trait LogBuilder[F[_]] {
  def log(msg: String): F[Unit]
  def log(ex: Throwable)(msg: String): F[Unit]
  def withArg[T: StructureEncoder](key: String, value: => T): LogBuilder[F]
}

trait WhenEnabledLogBuilder[F[_]] { self =>
  def apply(f: LogBuilder[F] => F[Unit]): F[Unit]

  def log(msg: String): F[Unit] = apply(_.log(msg))
  def log(ex: Throwable)(msg: String): F[Unit] = apply(_.log(ex)(msg))

  def withArg[T: StructureEncoder](
      key: String,
      value: => T
  ): WhenEnabledLogBuilder[F] = {
    new WhenEnabledLogBuilder[F] {
      override def apply(f: LogBuilder[F] => F[Unit]): F[Unit] = self {
        logBuilder =>
          f(logBuilder.withArg(key, value))
      }
    }
  }

  def computeArg[T: StructureEncoder](key: String)(
      fv: F[T]
  )(implicit F: FlatMap[F]): WhenEnabledLogBuilder[F] = {
    new WhenEnabledLogBuilder[F] {
      override def apply(f: LogBuilder[F] => F[Unit]): F[Unit] = {
        self { logBuilder =>
          fv.flatMap { value =>
            f(logBuilder.withArg(key, value))
          }
        }
      }
    }
  }
}
