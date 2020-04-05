package playground

import java.util.NoSuchElementException

import scala.annotation.tailrec

object Inheritance extends App {

  /**
   * These took me a while to figure out.
   *
   * I started out with:
   * class Vehicle(wheels: Int) {}
   * class Plane(wings: Int) extends Vehicle {}
   * This raises a warning: `Unspecified value parameters: wheels: Int`
   *
   * So I tried:
   * class Vehicle(wheels: Int) {}
   * class Plane(wings: Int) extends Vehicle(wheels) {}
   * This raises a warning: `Cannot resolve symbol wheels`
   *
   * But this works:
   * class Vehicle(wheels: Int) {}
   * class Plan(wings: Int) extends Vehicle(wheels=3) {}
   *
   * But to have a fully parameterized `wheels` argument:
   */
  class Vehicle(wheels: Int) {
    def drive(): Unit = println("Road trip")
    def drive(gear: Int): Int = {
      println(s"Gear $gear")
      0
    }
  }

  class Plane(wings: Int, wheels: Int) extends Vehicle(wheels) {
    override def drive(): Unit = println("To the skies")
  }

  val vehicle = new Vehicle(4)
  val plane = new Plane(2, 3)
  vehicle.drive()
  vehicle.drive(2)
  plane.drive()

  /**
   * To prevent overrides (for ex: you have built a library)
   * you can use:
   * - final over the method.
   * - final over the class.
   * - sealed over the class.
   *
   * sealed* allows overrides in the current file.
   * https://docs.scala-lang.org/tour/pattern-matching.html#sealed-classes
   */

  trait Gasoline {
    def fuelOverMessage(): Unit = println("Need petroleum")
  }

  trait Electric {
    def fuelOverMessage(): Unit = println("Battery over")
  }

  class TraditionalCar extends Vehicle(wheels = 4) with Gasoline {}
  class ElectricCar extends Vehicle(wheels = 4) with Electric {}

  val traditionalCar = new TraditionalCar()
  val electricCar = new ElectricCar()
  traditionalCar.fuelOverMessage()
  electricCar.fuelOverMessage()

  val MISSING_FIELD_MESSAGE = "The item you are trying to access does not exist."

  /**
   * Doing the following instead breaks things:
   *
   * class HybridCar extends Vehicle(wheels = 4) with Gasoline with Electric {}
   * val hybridCar = new HybridCar()
   * hybridCar.fuelOverMessage
   *
   * Since the same method with same signature is available on both the traits
   * there is a conflict of choice for the class trying to use both.
   */
  final case class NotApplicable(private val message: String = MISSING_FIELD_MESSAGE)
    extends Exception(message) {}

  abstract class AList {
    def head(): AList
    def tail(): Int
    def isEmpty: Boolean
    def add(item: Int): AList
  }

  class EmptyNode extends AList {
    def head(): AList = throw NotApplicable()
    def tail(): Int = throw NotApplicable()
    def isEmpty: Boolean = true
    def add(item: Int): AList = new Node(this, item)
  }

  class Node (head: AList, tail: Int) extends EmptyNode {
    override def head(): AList = head
    override def tail(): Int = tail
    override def isEmpty: Boolean = false
  }

  class ProtoList extends EmptyNode {
    var nodes: AList = new EmptyNode
    var empty = true
    override def head(): AList = nodes.head()
    override def tail(): Int = nodes.tail()
    override def isEmpty: Boolean = empty
    override def add(item: Int): AList = {
      empty = false
      nodes = new Node(nodes, item)
      nodes
    }

    private def tailToRoot(node: AList): String = {
      @tailrec
      def fn(node: AList, acc: String): String = {
        if (node.isEmpty) s"NULL->$acc"
        else fn(node.head(), s"${node.tail()}->${acc}")
      }
      fn(node, "*")
    }

    override def toString: String = {
      this.tailToRoot(nodes)
    }
  }

  val protoList = new ProtoList()
  protoList.add(1)
  protoList.add(2)
  protoList.add(3)
  println(protoList)
}