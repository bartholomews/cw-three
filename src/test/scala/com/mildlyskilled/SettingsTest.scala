package com.mildlyskilled

import org.scalatest.FunSuite

/**
  * Test for settings.
  */
class SettingsTest extends FunSuite {
  var settings = new Settings

  test("Should construct settings with correct default values") {
    assert(settings.antiAliasing == 6)
    assert(settings.width == 800)
    assert(settings.height == 600)
    assert(settings.renderLineWidth == 10)
    assert(settings.ambient == 0.9f)
    assert(settings.backgroundColor == Colour.black)
    assert(settings.renderNodeNumber == 10)
  }

}
