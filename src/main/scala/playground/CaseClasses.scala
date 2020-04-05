package playground

object CaseClasses extends App {
  /**
   * There are a bunch of features that
   * are available on case classes
   */

  /**
   * 1. Params are fields.
   * name is already a class property
   */
  case class Fruit(name: String, flavor: String)
  val apple = Fruit("apple", "crispy-sweet")
  println(apple.name)

  /**
   * 2. toString is readable
   */
  println(apple)

  /**
   * 3. Equality check over values
   * This doesn't work with classes without case keyword
   * the equality checks the location of the objects in that case.
   */
  println(Fruit("apple", "crispy-sweet") == Fruit("apple", "crispy-sweet"))

  /**
   * 4. Built in copy method
   */
  val greenApple = apple.copy("greenApple")
  println(greenApple)

  /**
   * 5. case class objects have companion objects
   * notice the lack of new
   */
  println(Fruit("Orange", "citrus"))

  /**
   * 6. Extractable via pattern matching
   */
}
