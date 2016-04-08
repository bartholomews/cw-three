package com.mildlyskilled

import org.scalactic.TolerantNumerics
import org.scalatest.FunSuite

/**
  * Test class for camera object
  */
class CameraTest extends FunSuite {
  var cam = new Camera(Vector.origin, 90f)

  val delta = 1e-9f
  implicit val doubleEq = TolerantNumerics.tolerantDoubleEquality(delta)

  test("Should construct camera object correctly") {
    assert(cam.position == Vector.origin)
    assert(cam.viewAngle == 90f)
  }

  test("Should calculate frustum correctly") {
    assert(cam.frustum === (math.Pi/4).toFloat)

    cam = new Camera(Vector.origin, 180f)
    assert(cam.frustum === (math.Pi/2).toFloat)

    cam = new Camera(Vector.origin, 360f)
    assert(cam.frustum === (math.Pi).toFloat)
  }
}

