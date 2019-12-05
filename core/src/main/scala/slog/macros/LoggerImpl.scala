package slog.macros

import slog.LogBuilder

import scala.reflect.macros.blackbox

object LoggerImpl {
  def infoImpl[F[_]](c: blackbox.Context): c.Expr[LogBuilder[F]] = {
    impl[F](c)(c.universe.TermName("info"))
  }

  def debugImpl[F[_]](c: blackbox.Context): c.Expr[LogBuilder[F]] = {
    impl[F](c)(c.universe.TermName("debug"))
  }

  def traceImpl[F[_]](c: blackbox.Context): c.Expr[LogBuilder[F]] = {
    impl[F](c)(c.universe.TermName("trace"))
  }

  def warnImpl[F[_]](c: blackbox.Context): c.Expr[LogBuilder[F]] = {
    impl[F](c)(c.universe.TermName("warn"))
  }

  def errorImpl[F[_]](c: blackbox.Context): c.Expr[LogBuilder[F]] = {
    impl[F](c)(c.universe.TermName("error"))
  }

  private def impl[F[_]](c: blackbox.Context)(level: c.universe.TermName): c.Expr[LogBuilder[F]] = {
    import c.universe._
    val file = Literal(Constant(c.enclosingUnit.source.path.toString))
    val line = Literal(Constant(c.enclosingPosition.line))
    val tree = q"""
        ${c.prefix}.underlying.$level.context("file", $file).context("line", $line)
       """
    c.Expr(tree)
  }
}
