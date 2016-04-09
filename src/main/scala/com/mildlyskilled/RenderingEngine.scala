package com.mildlyskilled

import akka.actor.Actor
import org.apache.commons.lang.time.StopWatch

/**
  * This is a worker actor, moving the computation into this class
  */
class RenderingEngine(scene: Scene, counter: Counter, camera: Camera, settings: Settings) extends Actor {
  val objects = scene.objects
  val lights = scene.lights

  val aa = settings.antiAliasing
  val width = settings.width
  val height = settings.height

  val stopWatch = new StopWatch

  def receive = {
    case Render(startY, endY , id) => {
      traceImage(startY, endY, id)
    }
  }

  def traceImage(startY: Int, endY: Int, id: Int) {
    stopWatch.start()
    println("Render Node " + id + " started")

    for (y <- startY until endY) {
      for (x <- 0 until width) {
        var resultColor = Colour.black

        for (dx <- 0 until aa) {
          for (dy <- 0 until aa) {

            val dir = Vector(
              (camera.sinF * 2 * ((x + dx.toFloat / aa) / width - .5)).toFloat,
              (camera.sinF * 2 * (height.toFloat / width) * (.5 - (y + dy.toFloat / aa) / height)).toFloat,
              camera.cosF.toFloat).normalized

            val color = trace(Ray(camera.position, dir)) / (aa * aa)
            resultColor += color
          }
        }

        if (Vector(resultColor.r, resultColor.g, resultColor.b).norm < 1)
          counter.darkCount.incrementAndGet()
        if (Vector(resultColor.r, resultColor.g, resultColor.b).norm > 1)
          counter.lightCount.incrementAndGet()
        sender ! Result(x, y, resultColor)
      }
    }
    stopWatch.stop()
    println("Render Node " + id + " finished in " + stopWatch.getTime + " ms")
    stopWatch.reset()
  }

  def shadow(ray: Ray, l: Light): Boolean = {
    val distSquared = (l.loc - ray.orig).normSquared
    intersections(ray).foreach {
      case (v, o) =>
        if ((v - ray.orig).normSquared < distSquared)
          return true
    }
    false
  }

  // Compute the color contributed by light l at point v on object o.
  def shade(ray: Ray, l: Light, v: Vector, o: Shape): Colour = {
    val toLight = Ray(v, (l.loc - v).normalized)

    val N = o.normal(v)

    // Diffuse light
    if (shadow(toLight, l) || (N dot toLight.dir) < 0)
      Colour.black
    else {
      // diffuse light
      val diffuse = o.colour * (N dot toLight.dir)

      println("ray " + ray)
      println("diffuse " + diffuse)

      // specular light
      val R = reflected(-toLight.dir, N)
      val len = ray.dir.norm * R.norm

      val specular = if (len > 1e-12) {
        // Want the angle between R and the vector TO the eye

        val cosalpha = -(ray.dir dot R) / len

        val power = if (cosalpha > 1e-12) math.pow(cosalpha, o.shine).toFloat else 0.0f

        if (power > 1e-12) {
          val scale = o.reflect * power
          l.colour * o.specular * scale
        }
        else
          Colour.black
      }
      else
        Colour.black

      println("specular " + specular)

      val color = diffuse + specular

      println("color " + color + " 0x" + color.rgb.toHexString)

      color
    }
  }

  def reflected(v: Vector, N: Vector): Vector = v - (N * 2.0f * (v dot N))

  def intersections(ray: Ray) = objects.flatMap {
    o => o.intersect(ray).map { v => (v, o)}
  }

  def closestIntersection(ray: Ray) = intersections(ray).sortWith {
    case ((v1, o1), (v2, o2)) => {
      val q1 = (v1 - ray.orig).normSquared
      val q2 = (v2 - ray.orig).normSquared
      q1 < q2
    }
  }.headOption

  val maxDepth = 3

  def trace(ray: Ray): Colour = trace(ray, maxDepth)

  private def trace(ray: Ray, depth: Int): Colour = {
    counter.rayCount.incrementAndGet()

    // Compute the intersections of the ray with every object, sort by
    // distance from the ray's origin and pick the closest to the origin.
    val r = closestIntersection(ray)

    r match {
      case None => {
        // If no intersection, the color is black
        settings.backgroundColor
      }
      case Some((v, o)) => {
        // Compute the color as the sum of:

        counter.hitCount.incrementAndGet()

        // The contribution of each point light source.
        var c = lights.foldLeft(Colour.black) {
          case (c, l) => c + shade(ray, l, v, o)
        }

        // The contribution of the ambient light.
        c += o.colour * settings.ambient

        // Return the color.
        c
      }
    }
  }
}
