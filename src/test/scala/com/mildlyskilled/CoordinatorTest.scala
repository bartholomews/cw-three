package test.scala.com.mildlyskilled

import akka.actor.{Props, ActorSystem}
import akka.testkit.{TestActorRef, ImplicitSender, TestKit}
import com.mildlyskilled._
import org.scalatest.{MustMatchers, BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created to test Coordinator Actor
  */
class CoordinatorTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll{

  def this() = this(ActorSystem("CoordinatorTest"))

  val (inFile, outFile) = ("src/main/resources/input.dat", "output.png")
  val settings = new Settings(800, 600, 10, 4, 0.6f, Colour.black, 10)
  val counter = new Counter
  val camera = new Camera(Vector.origin, 90f)
  val scene = SceneLoader.load(inFile)

  val image = new Image(settings.width, settings.height)

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A Coordinator actor" must {

    val testCoordinator = TestActorRef(new Coordinator(image, outFile, scene, settings, counter, camera))
    val realCoordinator = testCoordinator.underlyingActor

    "have a worker router" in {
      assert(realCoordinator.renderNodesRouter != null)
    }

    "have a stopwatch" in {
      assert(realCoordinator.stopWatch != null)
    }

    "be waiting for the correct number of pixels" in {
      assert(realCoordinator.waiting === 800*600)
    }

    "have start and end points for the pixel rendering" in {
      assert(realCoordinator.startOfSegments != null)
      assert(realCoordinator.endOfSegments != null)
    }
  }
}
