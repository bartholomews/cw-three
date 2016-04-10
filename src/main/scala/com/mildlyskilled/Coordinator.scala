package com.mildlyskilled

import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.{BalancingPool, RoundRobinPool, SmallestMailboxPool}
import org.apache.commons.lang.time.StopWatch

/**
  * Coordinator made into an Actor
  */

class Coordinator(image: Image, outFile: String, scene: Scene, settings: Settings, counter: Counter, camera: Camera)
  extends Actor with ActorLogging {

  var waiting = image.width * image.height //for counting rendered pixels

  val stopWatch = new StopWatch

  //render node actors with round robin routing algorithm
  val renderNodesRouter = context.actorOf(Props(new RenderingEngine(scene, counter, camera, settings))
    .withRouter(RoundRobinPool(settings.nodes)), name = "renderNodes")

  val startOfSegments = for (i <- 0 to image.height by settings.regionHeight) yield i
  val endOfSegments = startOfSegments.tail

  def set(xPos: Int, yPos: Int, color: Colour) = {
    image(xPos, yPos) = color
    waiting -= 1
  }

  def print = {
    assert(waiting == 0)
    image.print(outFile)
  }

  def receive = {
    case Start =>
      stopWatch.start()
      for (i <- endOfSegments.indices)
        renderNodesRouter ! Render(startOfSegments(i), endOfSegments(i), i)

    case Result(xPos, yPos, color) =>
      set(xPos, yPos, color)

      if (waiting == 0) {
        printResults()
        stopWatch.stop()
        log.info("Processing time: " + stopWatch.getTime + " ms")

        context stop self
        context.system.terminate()
      }
  }

  def printResults() = {
    println("\nImage printed out")
    println("rays cast " + counter.rayCount)
    println("rays hit " + counter.hitCount)
    println("light " + counter.lightCount)
    println("dark " + counter.darkCount)
  }

}