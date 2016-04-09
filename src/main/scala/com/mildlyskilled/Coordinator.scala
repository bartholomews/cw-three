package com.mildlyskilled

import akka.actor.{Props, Actor}
import akka.routing.RoundRobinPool

/**
  * Coordinator made into an Actor
  */

class Coordinator(im: Image, outFile: String, scene: Scene, counter: Counter,
                  camera: Camera, settings: Settings) extends Actor {
  val image = im
  val outfile = outFile
  var waiting = im.height * im.width

  val renderNodesRouter = context.actorOf(Props(new RenderingEngine(scene, counter, camera, settings))
    .withRouter(RoundRobinPool(settings.renderNodeNumber)), name = "renderNodes")

  val startOfSegments = for (i <- 0 to image.height by settings.renderLineWidth) yield i
  val endOfSegments = startOfSegments.tail

  def set(x: Int, y: Int, c: Colour) = {
    image(x, y) = c
  }

  def print = {
    assert(waiting == 0)
    image.print(outfile)
  }

  override def receive: Receive = {

    case Start =>
      for (i <- endOfSegments.indices) renderNodesRouter ! Render(startOfSegments(i), endOfSegments(i), i)

    case Result(x, y, colour) =>
      set(x, y, colour)
      waiting -= 1

      if (waiting == 0) {
        println("rays cast " + counter.rayCount.get())
        println("rays hit " + counter.hitCount.get())
        println("light " + counter.lightCount.get())
        println("dark " + counter.darkCount.get())

        println("Image printed out")
        context.system.terminate()
        context stop self
      }
  }
}