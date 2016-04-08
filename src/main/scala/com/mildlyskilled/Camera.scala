package com.mildlyskilled

/**
  * Class representing the camera object in 3D scene.
  */
class Camera(pos: Vector, angle: Float) {
  val position = pos
  val viewAngle = angle // viewing angle

  val frustum = (0.5 * viewAngle * math.Pi / 180).toFloat
  val cosF = math.cos(frustum)
  val sinF = math.sin(frustum)
}
