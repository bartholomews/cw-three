package com.mildlyskilled

import org.scalatest.FunSuite

/**
  * Test class for scene loader.
  */
class SceneLoaderTest extends FunSuite {
  val file = "src/main/resources/input.dat"

  test("Should load 2 lights and 3 objects form a file") {
    val scene = SceneLoader.load(file)
    assert(scene.lights.length == 2)
    assert(scene.objects.length == 3)
  }
}
