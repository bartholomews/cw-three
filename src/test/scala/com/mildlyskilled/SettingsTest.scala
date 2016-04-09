package com.mildlyskilled

import org.scalatest.{FunSuite}

/**
  * Test for settings.
  */
class SettingsTest extends FunSuite{
  var settings = new Settings(800, 600, 10, 4, 0.6f, Colour.black, 10)

  test("Should construct settings with correct default values") {
    assert(settings.antiAliasing == 4)
    assert(settings.width == 800)
    assert(settings.height == 600)
    assert(settings.regionHeight == 10)
    assert(settings.ambient == 0.6f)
    assert(settings.bgColor == Colour.black)
    assert(settings.nodes == 10)
  }

}
