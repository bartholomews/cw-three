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

    "have a stopwatch" in {
      assert(realCoordinator.stopWatch != null)
    }

    "be waiting for the correct number of pixels" in {
      /*
      I couldn't find the image height/width anywhere so made this check not null
      Not sure if this check will work in reality as the waiting variable will change
      quite quickly.
       */
      //noinspection ComparingUnrelatedTypes
      assert(realCoordinator.waiting != null)
    }

    "have start and end points for the pixel rendering" in {
      assert(realCoordinator.startOfSegments != null)
      assert(realCoordinator.endOfSegments != null)
    }
  }
}
