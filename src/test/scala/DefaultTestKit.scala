import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

class DefaultTestKit extends TestKit(ActorSystem("Test")) with WordSpecLike with ImplicitSender with Matchers with BeforeAndAfterAll {
  override def afterAll() = system.shutdown()
}
