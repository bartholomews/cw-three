package com.mildlyskilled

import akka.actor.{ActorSystem, Props}
import akka.testkit.{EventFilter, ImplicitSender, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}

/**
  * Created to test Coordinator Actor
  */
class CoordinatorTest(_system: ActorSystem) extends TestKit(_system) with ImplicitSender
  with WordSpecLike with MustMatchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("CoordinatorTest",
    /*
     Subscribe to the EventStream (where all the log messages go to)
     to assert the presence of the log message itself.
     @see http://rerun.me/2014/09/29/akka-notes-logging-and-testing/
      */
    ConfigFactory.parseString("""
                               akka {
                               loglevel = "WARNING"
                                loggers = ["akka.testkit.TestEventListener"]
                                test {
                                  filter-leeway = 6s
                                }
                               } """)))

  // =====================================================================
  val (inFile, outFile) = ("src/main/resources/input.dat", "output.png")
  val settings = new Settings(800, 600, 10, 4, 0.6f, Colour.black, 10)
  val counter = new Counter
  val camera = new Camera(Vector.origin, 90f)
  val scene = SceneLoader.load(inFile)
  val image = new Image(settings.width, settings.height)
  // =====================================================================

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

    "decrease val waiting after receiving one Result message" in {
      within(200 millis) {
        // value of val waiting when the Coordinator is first initialised
        val firstWaitingValue = settings.height * settings.width
        // value of val waiting after one Result received
        val nextWaitingValue = firstWaitingValue - 1
        assert(realCoordinator.waiting == firstWaitingValue)
        testCoordinator ! Result(0, 0, Colour.black)
        assert(realCoordinator.waiting == nextWaitingValue)
      }
    }


  }
}
