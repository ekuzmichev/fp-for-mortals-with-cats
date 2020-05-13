package ru.ekuzmichev.fp_for_mortals_with_cats

import scala.concurrent.Future
import scala.language.higherKinds

object `1.1 Abstracting over Execution` {
  trait Foo[C[_]] {
    def create(i: Int): C[Int]
  }

  object FooList extends Foo[List] {
    override def create(i: Int): List[Int] = List(i)
  }

  type EitherString[T] = Either[String, T]

  object FooEitherString extends Foo[EitherString] {
    override def create(i: Int): EitherString[Int] = Right(i)
  }

  object FooEitherStringWithKindProjector extends Foo[Either[String, *]] {
    override def create(i: Int): Either[String, Int] = Right(i)
  }

  import cats.Id

  object FooId extends Foo[Id] {
    override def create(i: Int): Id[Int] = i
  }

  trait Terminal[C[_]] {
    def read: C[String]
    def write(t: String): C[Unit]
  }

  object TerminalSync extends Terminal[Id] {
    def read: String           = "hello"
    def write(t: String): Unit = ()
  }

  object TerminalAsync extends Terminal[Future] {
    def read: Future[String]           = Future.successful("hello")
    def write(t: String): Future[Unit] = Future.successful(())
  }

  trait Execution[C[_]] {
    def chain[A, B](c: C[A])(f: A => C[B]): C[B]
    def create[B](b: B): C[B]
  }

  import cats.Monad
  import cats.syntax.flatMap._
  import cats.syntax.applicative._

  class MonadExecution[C[_]: Monad] extends Execution[C] {
    override def chain[A, B](c: C[A])(f: A => C[B]): C[B] = c.flatMap(f)
    override def create[B](b: B): C[B] = b.pure
  }

  object Execution {
    implicit class Ops[A, C[_]](c: C[A]) {
      def flatMap[B](f: A => C[B])(implicit e: Execution[C]): C[B] = e.chain(c)(f)
      def map[B](f: A => B)(implicit e: Execution[C]): C[B]        = e.chain(c)(f andThen e.create)
    }
  }

  def echo1[C[_]](t: Terminal[C], e: Execution[C]): C[String] =
    e.chain(t.read) { in: String => e.chain(t.write(in)) { _: Unit => e.create(in) } }

  import Execution._

  def echo2[C[_]](implicit t: Terminal[C], e: Execution[C]): C[String] =
    for {
      in <- t.read
      _ <- t.write(in)
    } yield in
}
