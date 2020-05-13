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
        for { i <- a ; j <- b } println(s"$i $j")
      }
    }
}
