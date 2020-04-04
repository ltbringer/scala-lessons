package playground

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
    def drive = println("Road trip")
    def drive(gear: Int): Int = {
      println(s"Gear $gear")
      0
    }
  }

  class Plane(wings: Int, wheels: Int) extends Vehicle(wheels) {
    override def drive = println("To the skies")
  }

  val vehicle = new Vehicle(4)
  val plane = new Plane(2, 3)
  vehicle.drive
  vehicle.drive(2)
  plane.drive

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
}