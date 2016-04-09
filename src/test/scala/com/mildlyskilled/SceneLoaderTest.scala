package com.mildlyskilled

import java.io.FileNotFoundException

import org.scalatest.FunSuite

/**
  * Test class for scene loader.
  */
class SceneLoaderTest extends FunSuite {
  val validConfig = "src/test/resources/valid.dat"
  val noLights = "src/test/resources/noLights.dat"
  val noObjects = "src/test/resources/noObjects.dat"
  val falseConfig = "src/test/resources/falseConfig.dat"
  var scene: Scene = _

  test("Should load 6 lights and 6 objects form a file") {
    scene = SceneLoader.load(validConfig)
    assert(scene.lights.length == 6)
    assert(scene.objects.length == 6)
  }

  test("Should throw an exception if file is missing") {
    intercept[FileNotFoundException]{
      scene = SceneLoader.load("missing.file")
    }
  }

  test("Should throw an exception if no objects in file") {
    intercept[RuntimeException]{
      scene = SceneLoader.load(noObjects)
    }
  }

  test("Should throw an exception if no lights in file") {
    intercept[RuntimeException]{
      scene = SceneLoader.load(noLights)
    }
  }

  test("Should throw an exception if lights and objects present but false config") {
    intercept[RuntimeException]{
      scene = SceneLoader.load(falseConfig)
    }
  }
}
