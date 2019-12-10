package example

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import slog.{LoggerFactory, LoggingContext}

class Example[F[_]](
    loggerFactory: LoggerFactory[F],
    loggingContext: LoggingContext[F]
)(implicit F: Monad[F]) {
  private[this] val logger = loggerFactory.make[Example[F]]
  // or
  // private [this] val logger = loggerFactory.make("my-logger")

  // example output json does not include timestamps, log levels, thread name,
  // logger names etc to keep things simple. They are of course included in the real
  // output.
  def foo: F[Unit] = {
    // simple log message. Might take from scope.
    // {"message": "Hello world!!", "file": "Example.scala", "line": 19}
    logger.info("Hello world!!") >>
      // Provide extract context to the log message
      // {"message": "Hello", "file": "Example.scala", "line": 22, "correlation_id": "<VALUE>"}
      logger.info.withArg("correlation_id", "<VALUE>").log("Hello") >>
      // Provide extra context and an exception
      // {"message": "Hello", "file": "Example.scala", "line": 25, "correlation_id": "<VALUE>", "stack_trace": "Exception ..."}
      logger.info
        .withArg("correlation_id", "<VALUE>")
        .log(new Exception)("Hello") >>
      loggingContext
        .withArg("correlation_id", "<VALUE>")
        .withArg("request_id", "<VALUE>")
        .use {
          // {"message": "test", "file": "Example.scala", "line": 33, "correlation_id": "<VALUE>", "request_id": "<VALUE>", "stack_trace": "Exception ..."}
          logger.debug(new Exception)("test") >>
            // {"message": "test2", "file": "Example.scala", "line": 35, "correlation_id": "<VALUE>", "request_id": "<VALUE>"}
            logger.info("test2")
        } >>
      logger.trace.whenEnabled { logBuilder =>
        // low level method how to do things only when certain log level is enabled
        for {
          value <- superExpensive
          _ <- logBuilder
            .withArg("expensive", value)
            .log("This message was very expensive to compute!")
        } yield ()
      } >>
      logger.trace.whenEnabled // higher level API
        .computeArg("expensive")(superExpensive)
        .log("This message was very expensive to compute!")
  }

  val superExpensive: F[String] = ???

  def bar: F[Unit] = {
    // We can use case classes/sealed traits as context as well!
    // We use automatic typeclass deriviation here (hence the import), but
    // one can use semi automatic one as well (similar to circe)
    final case class Bar(values: List[Double])
    final case class Foo(value: Int, that: String, bar: Bar)
    import slog.generic.auto._
    // {"message": "test", "file": "Example.scala", "line": 47, "foo_bar": {"value": 10, "that": "foobar", "bar": {"values": [42.0]}}}
    logger.trace
      .withArg("foo_bar", Foo(10, "foobar", Bar(List(42.0))))
      .log("test")

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
