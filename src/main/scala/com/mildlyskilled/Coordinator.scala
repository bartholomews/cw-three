package com.mildlyskilled

import akka.actor.Actor

/**
  * Coordinator made into an Actor
  */

class Coordinator(im: Image, outFile: String) extends Actor {
  val image = im
  val outfile = outFile
  var waiting = im.height * im.width



  // TODO: make set a message
  def set(x: Int, y: Int, c: Colour) = {
    image(x, y) = c
    waiting -= 1
  }

  def print = {
    assert(waiting == 0)
    image.print(outfile)
  }

  override def receive: Receive = ???
}