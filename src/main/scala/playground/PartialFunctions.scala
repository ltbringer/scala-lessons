package playground

object PartialFunctions extends App {
  /**
   * Works on any integer.
   */
  val inc = (x: Int) => x + 1

  /**
   * Lets try a restriction on args [1-5]
   */
  val restrictedInc = (x: Int) => {
    if (x > 5 || x < 1)  {
      throw new Exception
    }
    x + 1
  }

  /**
   * This will fail if the argument is not in the
   * range [0-5]
   */
  val partialFunc: PartialFunction[Int, Int] = {
    case arg if 0 until 6 contains arg => arg + 1
  }
  println(partialFunc(1))

  /**
   * Tests if a partial function can be run
   * for the given arguments.
   */
  println(partialFunc.isDefinedAt(67))

  /**
   * lift returns Option[Int]
   * instead of MatchError
   */
  println(partialFunc.lift(2))
  println(partialFunc.lift(28))

  /**
   * This extends a partial function,
   * such that more cases can be handled if needed.
   */
  val chained = partialFunc orElse[Int, Int] {
    case 45 => 67
  }
  println(chained(4))
  println(chained(45))
}
