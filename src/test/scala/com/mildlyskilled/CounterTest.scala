package com.mildlyskilled

import org.scalatest.FunSuite

/**
  * Test class for counter.
  */
class CounterTest extends FunSuite {
  var counter = new Counter

  test("Should construct counter with correct default values") {
    assert(counter.darkCount == 0)
    assert(counter.hitCount == 0)
    assert(counter.lightCount == 0)
    assert(counter.rayCount == 0)
  }

}
