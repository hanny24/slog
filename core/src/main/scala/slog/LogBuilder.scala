package slog

import cats.Monad
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

  def computeArg[T: StructureEncoder](key: String)(
      fv: F[T]
  )(implicit F: Monad[F]): WhenEnabledLogBuilder[F] = {
    new WhenEnabledLogBuilder[F] {
      override def apply(f: LogBuilder[F] => F[Unit]): F[Unit] = {
        fv.flatMap { value =>
          self(f.compose[LogBuilder[F]](_.withArg(key, value)))
        }
      }
    }
  }
}
