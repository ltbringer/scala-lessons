package playground

import scala.util.Random

object PatternMatching extends App {
  /**
   * Switch case.
   */
  val random = new Random()
  val x = random.nextInt(10)
  val description = x match {
    case 0 => "I can be your hero"
    case 1 => "A number"
    case 2 => "Two is company"
    case 3 => "Three is crowd"
    case _ => "..."
  }

  println(x, description)

  /**
   * Value decomposition and guards.
   */
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 25)
  val alice = Person("Alice", 8)

  def greetPerson(person: Person): String = {
    person match {
      case Person(n, a) if a > 10 => s"Hi, this is $n. I am $a years old."
      case Person(n, _) => s"Hi, this is $n. I am too young."
      case _ => "Hello"
    }
  }

  println(greetPerson(bob))
  println(greetPerson(alice))

  /**
   * The sealed class will issue a warning
   * when this is compiled:
   * "match may not be exhaustive ..."
   * This is not available on classes otherwise.
   */
  sealed class Color
  case class Red(hex: String) extends Color
  case class Blue(hex: String) extends Color
  val red: Color = Red("#F10052")
  val blue: Color = Blue("#1145CA")
  def colorChecker(color: Color): String = {
    color match {
      case Red(hex) => s"Red color of hex $hex"
    }
  }

  println(colorChecker(red))


}
