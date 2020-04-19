package playground

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

  waitNotifyProducerConsumer()
}
