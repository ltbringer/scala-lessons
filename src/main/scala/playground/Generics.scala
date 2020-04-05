package playground

import scala.annotation.tailrec

object Generics extends App {
  /**
   * Here we will re-write the same custom-list from inheritance example.
   * This time, using generics.
   *
   * WARNING: The following does not compile.
   */
  val MISSING_FIELD_MESSAGE = "The item you are trying to access does not exist."
  final case class NotApplicable(private val message: String = MISSING_FIELD_MESSAGE)
    extends Exception(message) {}

  abstract class AList[+T] {
    def head(): AList[T]
    def tail(): T
    def isEmpty: Boolean

    /**
     * Let's say we have:
     * - A type T
     * - Another type S which is a subclass of T
     * - Then [S >: T] means the method is bound
     * to subclasses of T.
     *
     * for example:
     * T = Vehicle
     * S = Car
     * A = Animal
     * add(Car) then, would be valid but not add(Animal),
     * also adding a Car returns a AList of type Car
     */
    def add[S >: T](item: S): AList[S]
  }

  class EmptyNode extends AList[Nothing] {
    def head(): AList[Nothing] = throw NotApplicable()
    def tail(): Nothing = throw NotApplicable()
    def isEmpty: Boolean = true
    def add[S >: Nothing](item: S): AList[S] = new Node(this, item)
  }

  class Node[+T] (head: AList[T], tail: T) extends AList[T] {
    override def head(): AList[T] = head
    override def tail(): T = tail
    override def isEmpty: Boolean = false
    def add[S >: T](item: S): AList[S] = new Node(this, item)
  }

  class ProtoList[T] extends AList[T] {
    var nodes: AList[T] = new EmptyNode
    var empty = true
    override def head(): AList[T] = nodes.head()
    override def tail(): T = nodes.tail()
    override def isEmpty: Boolean = empty

    /**
     * This is the section that doesn't compile.
     * We create nodes and specify its type to be AList[T].
     * In this function, we are trying to replace it with a
     * value of type Node[S], since item is of type S.
     *
     * tl;dr: Replacing [T] with [S] is not allowed.
     */
    override def add[S >: T](item: S): AList[S] = {
      empty = false
      nodes = new Node(nodes, item)
      nodes
    }

    private def tailToRoot(node: AList[T]): String = {
      @tailrec
      def fn(node: AList[T], acc: String): String = {
        if (node.isEmpty) s"NULL->$acc"
        else fn(node.head(), s"${node.tail()}->${acc}")
      }
      fn(node, "*")
    }

    override def toString: String = {
      this.tailToRoot(nodes)
    }
  }

  val protoListInts = new ProtoList[Int]()
  protoListInts.add(1)
  protoListInts.add(2)
  protoListInts.add(3)
  println(protoListInts)

  val protoListStr = new ProtoList[String]()
  protoListStr.add("a")
  protoListStr.add("b")
  protoListStr.add("c")
  println(protoListStr)
}
