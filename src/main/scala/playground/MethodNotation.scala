package playground
import scala.language.postfixOps

/**
 * Syntactic Sugar: Using operators as method names is allowed.
 */
object MethodNotation extends App {
  class Vehicle(name: String, wheels: Int) {
    private var fuel: String = ""
    def +(wheels: Int) = s"${this.wheels} -> ${this.wheels + wheels}"
    def unary_+(): Int = this.wheels + 1
    def uses(fuel: String): Unit = this.fuel = fuel
    def usesFuel(): String = s"${this.name} uses ${this.fuel}"
  }

  val car = new Vehicle("speedy", 4)

  /**
   * Scala allows creation of methods with
   * only operator symbols as name.
   */
  println(s"Adding more wheels: ${car + 2}")

  /**
   * Prefix notation on unary, unary_(operator)
   */
  println(s"Increment wheels by 1 ${+ car }")

  /**
   * In case a method has only one argument,
   * It can be invoked without `.` and brackets.
   */
  car uses "petrol"

  /**
   * Postfix notation example.
   * Needed additional import scala.language.postfixOps
   *
   * NOTE: This is not general practice.
   */
  println(car usesFuel)
}
