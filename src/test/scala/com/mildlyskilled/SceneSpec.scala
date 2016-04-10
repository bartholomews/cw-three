package com.mildlyskilled

import com.mildlyskilled.harness.DefaultHarness


class SceneSpec extends DefaultHarness {

  behavior of "SceneSpec"

  val inputFile = "src/test/resources/valid.dat"

  val scene = SceneLoader.load(inputFile)

}
