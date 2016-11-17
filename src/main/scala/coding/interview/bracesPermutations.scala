/* Given a number n, find all posible permutations where n pairs of braces are balanced.
 * For example:
 * n = 3
 * ()()(), ()(()), (())(), (()()), ((()))
 */

import scala.annotation.tailrec
import scala.collection.mutable.{ListBuffer => LB}

def bracesPermutations(i: Int): Set[String] = {
  fn_(i).map(_.mkString)
}

@tailrec def fn_(i: Int, current: Set[LB[String]] = Set(LB())): Set[LB[String]] = {
  if (i == 0) {
    current
  } else {
    val newResult = current.flatMap { solution =>
      val eachWrapped = solution.map { group =>
        ("(" + group + ")") +: (solution - group)
      }.toSet
      (eachWrapped + ("()" +: solution)) + LB("(" + solution.mkString + ")")
    }
    fn_(i - 1, newResult)
  }
}
