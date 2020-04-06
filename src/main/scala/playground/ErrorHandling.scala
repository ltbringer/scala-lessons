package playground

import scala.util.{Failure, Success, Try}

object ErrorHandling extends App {
  /**
   * The result is wrapped in a Failure, instead of crashing the program.
   */
  def crashingFunction(): String = throw new RuntimeException("THIS ALWAYS FAILS")
  def slowOptionalFunction(): String = "This works but is slow."
  val result = Try(crashingFunction())
  println(result)

  /**
   * A chain-able API allows wrapping up functions that might throw
   * exceptions.
   */
  val retryAble = Try(crashingFunction()).orElse(Try(slowOptionalFunction()))
  println(retryAble)

  /**
   * As a lib-creator, these features can be used like so:
   */
  def breakingFunction(): Try[String] = Failure(new RuntimeException)
  def slowFunction(): Try[String] = Success("This works")
  println(breakingFunction() orElse slowFunction())

  /**
   * Using filters can convert a success to failure.
   * Predicate does not hold for <value>.
   */
  println(slowFunction().filter(_ == "this doesn't work"))
}
