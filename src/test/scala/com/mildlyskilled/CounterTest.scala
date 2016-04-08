package com.mildlyskilled

import org.scalatest.FunSuite

/**
  * Test class for counter.
  */
class CounterTest extends FunSuite {
  var counter = new Counter

  test("Should construct counter with correct default values") {
    assert(counter.darkCount.get() == 0)
    assert(counter.hitCount.get() == 0)
    assert(counter.lightCount.get() == 0)
    assert(counter.rayCount.get() == 0)
  }

}
