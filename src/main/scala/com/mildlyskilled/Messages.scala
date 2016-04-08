package com.mildlyskilled

/**
  * Messages for Actors
  */
sealed trait RenderMessage

case class Result (x: Int, y: Int, color: Colour) extends RenderMessage
