package example

import cats.Monad
import cats.syntax.flatMap._
import slog.{LoggerFactory, LoggingContext}

class Example[F[_]](loggerFactory: LoggerFactory[F],
                    loggingContext: LoggingContext[F])(implicit F: Monad[F]) {
  private[this] val logger = loggerFactory.make[Example[F]]
  // or
  // private [this] val logger = loggerFactory.make("my-logger")

  // Every log message will use filename and line number as their context by default.
  def foo: F[Unit] = {
    // simple log message. Might take from scope.
    logger.info.msg("Hello world!!") >>
      // Provide extract context to the log message
      logger.info.context("correlation_id", "<VALUE>").msg("Hello") >>
      // Provide extra context and an exception
      logger.info
        .context("correlation_id", "<VALUE>")
        .msg(new Exception)("Hello") >>
      loggingContext
        .use("correlation_id", "<VALUE>")
        .use("request_id", "<VALUE>")
        .scoped {
          // both of the log messages will use "correlation_id" and "request_id" as context information
          logger.debug.msg(new RuntimeException)("test") >>
            logger.info.msg("test")
        }
  }

  def bar: F[Unit] = {
    // We can use case classes/sealed traits as context as well!
    // We use automatic typeclass deriviation here (hence the import), but
    // one can use semi automatic one as well (similar to circe)
    final case class Bar(values: List[Double])
    final case class Foo(value: Int, that: String, bar: Bar)
    import slog.generic.auto._
    logger.trace
      .context("foo_bar", Foo(10, "foobar", Bar(List(42.0))))
      .msg("Test")

    // it's also easily possible to debug failed typeclass deriviation.
    // consider following example:
    class Inner
    final case class Outer(inner: Inner)

    // StructureEncoder[Outer]
    // fails with: could not find implicit value for parameter ev: slog.StructureEncoder[Outer]

    // You just need to call derivation method directly and it should help you debug it:
    // genStructureEncoder[Outer]
    // fails with: magnolia: could not find StructureEncoder.Typeclass for type Inner
    //    in parameter 'inner' of product type Outer
    //    genStructureEncoder[Outer]
    F.unit
  }
}
