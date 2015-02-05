import akka.actor.{Actor, Props}
import akka.stream.scaladsl.Source
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class StreamSpec extends DefaultStreamTestKit {
  import StreamSpec._

  "Stream" should {
    "should throw correct exception" in {
      val f = Source(1 to 100)
        .transform(() => dump("test"))
        .map(_ / 0)
        .foreach(_ => Unit)

      whenReady(f.failed) { e => e shouldBe a [ArithmeticException] }
    }

    "should fold numbers after async actor processing" in {
      import akka.pattern.ask

      val actor = system.actorOf(Props[AsyncActor])
      implicit val timeout = Timeout(1 second)

      val f = Source(1 to 100)
        .mapAsync(i => (actor ? AsyncActor.Do(i)).mapTo[Int])
        .fold(0) { _ + _ }

      whenReady(f) { _ should equal(5050) }
    }

    "should fold string" in {
      val f = Source(List("a", "b", "c"))
        .fold("") { _ + _ }

      whenReady(f) { _ should equal("abc") }
    }

    "should be source" in {
      val f = Source(List("a", "b", "c"))

      f shouldBe a [Source[String]]
    }
  }
}

object StreamSpec {
  object AsyncActor {
    case class Do(i:Int)
  }

  class AsyncActor extends Actor {
    import StreamSpec.AsyncActor._
    import akka.pattern.pipe
    import context.dispatcher

    def receive = {
      case Do(i) => Future { i } pipeTo sender()
    }
  }
}
