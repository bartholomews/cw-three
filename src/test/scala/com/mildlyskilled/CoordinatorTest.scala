package test.scala.com.mildlyskilled

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import com.mildlyskilled._
import org.scalatest.{MustMatchers, BeforeAndAfterAll, Matchers, WordSpecLike}

/**
  * Created to test Coordinator Actor
  */
class CoordinatorTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll{

  def this() = this(ActorSystem("CoordinatorTest"))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Coordinator actor" must {

    val testCoordinator = TestActorRef[Coordinator]
    val realCoordinator = testCoordinator.underlyingActor

    "have a worker router" in {
      assert(realCoordinator.renderNodesRouter != null)
    }
  }
}
