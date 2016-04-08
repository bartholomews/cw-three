package com.mildlyskilled

import akka.actor.{Props, Actor}
import akka.routing.RoundRobinPool

/**
  * Coordinator made into an Actor
  */

class Coordinator(im: Image, outFile: String, scene: Scene, counter: Counter,
                  camera: Camera) extends Actor {
  val image = im
  val outfile = outFile
  var waiting = im.height * im.width

  val renderNodesRouter = context.actorOf(Props(new RenderingEngine(scene, counter, camera))
    .withRouter(RoundRobinPool(50)), name = "renderNodes")


  // TODO: make set a message
  def set(x: Int, y: Int, c: Colour) = {
    image(x, y) = c
    waiting -= 1
  }

  def print = {
    assert(waiting == 0)
    image.print(outfile)
  }

  override def receive: Receive = {
    case Result(x, y, colour) =>
      set(x, y, colour)
  }
}