package ru.ekuzmichev.fp_for_mortals_with_cats

object `2. For Comprehensions` {
  import scala.reflect.runtime.universe._

  val a, b, c = Option(1)

  val reifyResFlatMap: String =
    show {
      reify {
        for {
          i <- a
          j <- b
          if i >= j
          ij = i + j
          k  <- c
        } yield ij + k
      }
    }

  val reifyResForEach: String =
    show {
      reify {
        for { i <- a; j <- b } println(s"$i $j")
      }
    }

  import cats.data.OptionT
  import cats.instances.future._
  import cats.syntax.applicative._

  import scala.concurrent.{ ExecutionContext, Future }

  implicit val ec: ExecutionContext = ExecutionContext.global

  def liftFutureOption[A](f: Future[Option[A]]): OptionT[Future, A] = OptionT(f)
  def liftFuture[A](f: Future[A]): OptionT[Future, A]               = OptionT.liftF(f)
  def liftOption[A](o: Option[A]): OptionT[Future, A]               = OptionT(o.pure[Future])
  def lift[A](a: A): OptionT[Future, A]                             = liftOption(Option(a))

  import mouse.all._

  def getA: Future[Option[Int]] = Future.successful(Some(1))
  def getB: Future[Option[Int]] = Future.successful(Some(2))
  def getC: Future[Int]         = Future.successful(3)
  def getD: Option[Int]         = Some(4)

  val liftOptionTResult: OptionT[Future, Int] =
    for {
      a <- getA |> liftFutureOption
      b <- getB |> liftFutureOption
      c <- getC |> liftFuture
      d <- getD |> liftOption
      e <- 10 |> lift
    } yield a + b + c + d + e
}
