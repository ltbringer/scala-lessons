package playground

object ScalaObjects {
  /**
   * Here we understand that scala application
   * is a scala-object. Extending with App allows
   * our applications to become runnable. That is
   * because The entry point is supposed to have a
   * particular signature which is made available by
   * inheritance.
   */
  def main(args: Array[String]): Unit = {
    println("works?")
  }

  /**
   * The details of why this works is related to how
   * scala code compiles to JVM code where the entry
   * point is `public static void main(...)`
   * static is not available but the alternative is singleton objects
   * void translates to Unit.
   */

  /**
   * A class definition
   * @param name
   */
  class Bird(name: String) {}

  /**
   * The object Bird is a singleton of the Bird class.
   */
  object Bird {
    val N_WINGS = 2
    def apply(name: String): Bird = {
      println("factory method invoked.")
      new Bird(name)
    }
  }

  /**
   * Instead of java's static variables,
   * we have scala-objects, that allow us to use
   * data in a similar fashion.
   */
  println(Bird.N_WINGS)

  /**
   * If we compare two instances of bird class.
   * We see that they are not equal as they point to
   * different memory locations.
   */
  println(new Bird("jack") == new Bird("sparrow"))

  /**
   * But,
   * If we compare two values that contain the singleton or object Bird
   * They match.
   */
  val jack = Bird
  val sparrow = Bird
  println(jack == sparrow)

  /**
   * Apply in a singleton is used as a factory to create class objects.
   */
  println(Bird("parrot"))
}
