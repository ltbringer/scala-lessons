package playground

import scala.collection.mutable
import scala.util.Random

object ThreadCommunication extends App {

  class SimpleContainer {
    private var value = 0
    def isEmpty: Boolean = value == 0
    def set(newValue: Int): Unit = value = newValue
    def get: Int = {
      val result = value
      value = 0
      result
    }
  }

  /**
   * Prevents usage of values by consumer unless
   * produced by using a while loop.
   */
  def naiveProducerConsumer(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("[consumer] waiting ...")
      while(container.isEmpty) {
        println("[consumer] waiting >> ...")
      }
      println("[consumer]: consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(500)
      val value = 33
      println("[producer]: I have produced the value " + value)
      container.set(value)
    })

    consumer.start()
    producer.start()
  }

  // naiveProducerConsumer()

  /**
   * You can check the performance by running both separately.
   */
  def waitNotifyProducerConsumer(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("[consumer] waiting ...")
      container.synchronized {
        container.wait()
      }
      println("[consumer]: consumed " + container.get)
    })
    val producer = new Thread(() => {
      println("[producer] computing ...")
      Thread.sleep(2000)
      val value = 42
      container.synchronized {
        println("[producer] produced " + value)
        container.set(value)
        container.notify()
      }
    })
    consumer.start()
    producer.start()
  }

  // waitNotifyProducerConsumer()

  /**
   * Let's say we have:
   * - A limited capacity buffer.
   * - Multiple producers.
   * - Multiple consumers.
   */
  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread {
    /**
     * Since Thread already has access to Runnable
     * We can override run.
     */
    override def run(): Unit = {
      val random = new Random()
      while(true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println(s"[consumer:$id] buffer empty | waiting.")
            buffer.wait()
          }
          val x = buffer.dequeue()
          println(s"[consumer:$id] picked $x from the buffer")
          buffer.notify()
        }
        Thread.sleep(random.nextInt(250))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var i = 0

      while(true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"[producer:$id]: buffer is full | waiting.")
            buffer.wait()
          }
          println(s"[producer:$id] producing $i")
          buffer.enqueue(i)
          buffer.notify()
          i += 1
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  def multiProdCons(nConsumers:Int, nProducers: Int, capacity: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    (1 to nConsumers).foreach(i => new Consumer(i, buffer).start())
    (1 to nProducers).foreach(i => new Producer(i, buffer, capacity).start())
  }

  multiProdCons(3, 3, 4)
}
