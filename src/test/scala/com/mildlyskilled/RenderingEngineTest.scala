package com.mildlyskilled

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike, FunSuite}
import akka.testkit.{ImplicitSender, TestKit, TestActorRef}

/**
  * Created by Rustam Drake on 4/9/16.
  */
class RenderingEngineTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {
//  val renderingEngine = TestActorRef[RenderingEngine]

def this() = this(ActorSystem("RenderingEngineTest",
  /*
   Subscribe to the EventStream (where all the log messages go to)
   to assert the presence of the log message itself.
   @see http://rerun.me/2014/09/29/akka-notes-logging-and-testing/
   (As per CoordinatorTest)
    */
  ConfigFactory.parseString(
    """
                               akka {
                                loglevel = "DEBUG"
                                loggers = ["akka.testkit.TestEventListener"]
                                test {
                                  filter-leeway = 6s
                                }
                               } """)))

  //=====================================================================
  val (inFile, outFile) = ("src/main/resources/input.dat", "output.png")
  val settings = new Settings(800, 600, 10, 4, 0.6f, Colour.black, 10)
  val counter = new Counter
  val camera = new Camera(Vector.origin, 90f)
  val scene = SceneLoader.load(inFile)
  val image = new Image(settings.width, settings.height)
  //=====================================================================

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A RenderingEngine actor" must {

    val testRenderer = TestActorRef(new RenderingEngine(scene, counter, camera, settings))
    val realRenderer = testRenderer.underlyingActor

    val testCoordinator = TestActorRef(new Coordinator(image, outFile, scene, settings, counter, camera))
    val realCoordinator = testCoordinator.underlyingActor

    "have a stopwatch" in {
      assert(realRenderer.stopWatch != null)
    }


  }
}
