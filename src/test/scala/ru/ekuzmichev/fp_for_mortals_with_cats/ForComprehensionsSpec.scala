package ru.ekuzmichev.fp_for_mortals_with_cats

import org.scalatest.flatspec.AnyFlatSpec

class ForComprehensionsSpec extends AnyFlatSpec {
  it should "work" in {
    import `2. For Comprehensions`._

    println(reifyResFlatMap)
    println(reifyResForEach)
  }
}
