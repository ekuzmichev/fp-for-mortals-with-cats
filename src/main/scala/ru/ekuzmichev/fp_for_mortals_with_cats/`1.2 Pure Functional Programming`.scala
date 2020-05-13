package ru.ekuzmichev.fp_for_mortals_with_cats

object `1.2 Pure Functional Programming` {
  import `1.1 Abstracting over Execution`._

  final class IO[A](val interpret: () => A) {
    def map[B](f: A => B): IO[B]         = IO(f(interpret()))
    def flatMap[B](f: A => IO[B]): IO[B] = IO(f(interpret()).interpret())
  }

  object IO {
    def apply[A](a: => A): IO[A] = new IO(() => a)
  }

  implicit object TerminalIo extends Terminal[IO] {
    def read: IO[String]           = IO("hello")
    def write(t: String): IO[Unit] = IO(println(t))
  }

  implicit val executionIo: Execution[IO] = new Execution[IO]{
    override def chain[A, B](c: IO[A])(f: A => IO[B]): IO[B] = c.flatMap(f)
    override def create[B](b: B): IO[B] = IO(b)
  }

  val delayed: IO[String] = echo2[IO]
}
