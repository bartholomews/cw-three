package com.mildlyskilled

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by annabel on 09/04/16.
  */
class RenderingEngine(scene: Scene, counter: Counter, camera: Camera) extends Actor {
  override def receive: Receive = ???
}
