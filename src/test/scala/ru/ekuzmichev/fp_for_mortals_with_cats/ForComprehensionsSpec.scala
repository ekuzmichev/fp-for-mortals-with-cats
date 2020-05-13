package ru.ekuzmichev.fp_for_mortals_with_cats

import org.scalatest.Inside
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ForComprehensionsSpec extends AnyFlatSpec with ScalaFutures with Inside with Matchers {
  it should "work" in {
    import `2. For Comprehensions`._

    println(reifyResFlatMap)
    println(reifyResForEach)

    inside(liftOptionTResult.value.futureValue) { case Some(value) => value should be(20) }
  }
}
