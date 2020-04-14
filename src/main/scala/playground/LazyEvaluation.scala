package playground

object LazyEvaluation extends App {
  /**
   * Imagine the following line of code being compiled.
   *
   * val x: Int = throw new RuntimeException
   * This is going to throw an exception, but
   * here we see what the lazy keyword can do.
   * Do note, it is not meant to be used this way.
   */
  lazy val x: Int = throw new RuntimeException // This line compiles !

  /**
   * Lazy delays evaluation of values. When the value is
   * meant to go through computation, the evaluation starts.
   *
   * lazy val x: Int = throw new RuntimeException
   * println(x)
   *
   * Will throw exception again because x is now required by println
   */
}
