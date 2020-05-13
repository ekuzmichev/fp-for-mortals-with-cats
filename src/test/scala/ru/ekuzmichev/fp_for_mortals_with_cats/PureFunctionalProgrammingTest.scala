package ru.ekuzmichev.fp_for_mortals_with_cats

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PureFunctionalProgrammingTest extends AnyFlatSpec with Matchers{
  import `1.2 Pure Functional Programming`._

  it should "work" in {
    delayed.interpret() should be("hello")
  }
}
