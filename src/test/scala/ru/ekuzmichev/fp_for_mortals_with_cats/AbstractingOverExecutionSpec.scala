package ru.ekuzmichev.fp_for_mortals_with_cats

import org.scalatest.Inside
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class AbstractingOverExecutionSpec extends AnyFlatSpec with Inside with Matchers {
  it should "work" in {
    import `1.1 Abstracting over Execution`._

    inside(FooEitherStringWithKindProjector.create(1)) { case Right(value) => value should be(1) }
    inside(FooEitherString.create(1)) { case Right(value)                  => value should be(1) }
    FooId.create(1) should be(1)

    import cats.Id

    echo1[Id](TerminalSync, new MonadExecution[Id]) should be("hello")

    implicit val t: Terminal[Id]  = TerminalSync
    implicit val e: Execution[Id] = new MonadExecution[Id]

    echo2[Id] should be("hello")
  }
}
