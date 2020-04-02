package playground

import scala.annotation.tailrec

object TailRecursion extends App {


  def factorial(n: Int): BigInt = {
    @tailrec
    def fn(n: Int, acc: BigInt): BigInt = {
      if (n <= 1) acc
      else {
        /**
         * The interesting parallel here, as compared to the general
         * recursive factorial function is the use of `n * fn(n - 1)`
         *
         * Scala compiler (it seems all compilers that support TCO)
         * can optimize the call stack if the recursive call is the only
         * operation on the path.
         *
         * n * fn(n - 1) expresses an additional multiplication.
        */
        fn(n - 1, n * acc)
      }
    }
    fn(n, 1)
  }

  def strChain(str: String, n: Int): String = {
    @tailrec
    def fn(str: String, n: Int, acc: String): String = {
      if (n == 1) acc
      else fn(str, n - 1, s"$str$acc")
    }
    fn(str, n, str)
  }

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isComposite(n: Int, acc: Int): Boolean = {
      if (n % acc == 0) true
      else if (n % acc != 0) false
      else isComposite(n, acc - 1)
    }
    !isComposite(n, n / 2)
  }

  def fib(n: Int): Int = {
    @tailrec
    def fn(i: Int, a: Int, b: Int): Int = {
      if (i >= n) a
      else fn(i + 1, a + b, a)
    }
    if (n == 0) 0
    else if (n <= 2) 1
    else fn(1, 0, 1)
  }

  // Wouldn't work with the normal implementation
  println("factorial of 6 is:", factorial(6000))

  println("chain apple 10 times", strChain("apple", 10))
  println("Checking bunch of primes:", isPrime(31), isPrime(4), isPrime(133))
  println("fib(5)", fib(5))
}
