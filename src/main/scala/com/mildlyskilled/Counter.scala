package com.mildlyskilled

import java.util.concurrent.atomic.AtomicInteger

/**
  * Class for casted rays statistics. To avoid race condition, as this object is shared by multiple threads,
  * AtomicInteger is used, as it provides atomic decrement and increment.
  *
  */
class Counter {
  val rayCount = new AtomicInteger()
  val hitCount = new AtomicInteger()
  val lightCount = new AtomicInteger()
  val darkCount = new AtomicInteger()
}
