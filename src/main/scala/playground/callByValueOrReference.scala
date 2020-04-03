package playground

object callByValueOrReference extends App {
  def value(x: Long): Unit = {
    println(s"The value is $x")
    Thread.sleep(1000)
    println(s"The value is $x")
  }

  def reference(x: => Long): Unit = {
    println(s"The value is $x")
    Thread.sleep(1000)
    println(s"The value is $x")
  }

  /**
   * The values are identical because
   * the value of System.nanotime is evaluated
   * before passing to the function.
   */
  value(System.nanoTime())

  /**
   * The values are non-identical, since
   * `=>` passes the expression itself. `system.nanoTime()`
   * gets called twice in the function body.
   */
  reference(System.nanoTime())
}
