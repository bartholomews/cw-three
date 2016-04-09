package com.mildlyskilled

/**
  * Class for loading objects and lights from a configuration text file
  * into a Scene object.
  */
object SceneLoader{
  import java.io.{FileReader, LineNumberReader}

  import scala.annotation.tailrec

  /**
    * Reads config file and loads lights and objects into a Scene object.
    * @param file input file
    * @return new Scene object
    */
  def load(file: String): Scene = {
    val in = new LineNumberReader(new FileReader(file))
    val (objects, lights) = readLines(in, Nil, Nil)
    new Scene(objects, lights)
  }

  /**
    * Return list of lights and objects from a config file.
    * @param in line reader
    * @param objects list of shapes
    * @param lights list of lights
    * @return tuple of list of shapes and list of lights
    */
  @tailrec
  private def readLines(in: LineNumberReader, objects: List[Shape], lights: List[Light]): (List[Shape], List[Light]) = {
    val line = in.readLine
    if (line == null) {
      (objects, lights) match {
        case (Nil, _) => throw new RuntimeException("no objects")
        case (_, Nil) => throw new RuntimeException("no lights")
        case (os, ls) => (os.reverse, ls.reverse)
      }
    }
    else {
      val fields = line.replaceAll("#.*", "").trim.split("\\s+").filter(_ != "")
      fields.headOption match {
        case Some("sphere") =>
          val Array(x, y, z, rad, r, g, b, shine) = fields.tail.map(_.toFloat)
          readLines(in, Sphere(Vector(x, y, z), rad, Colour(r, g, b), shine) :: objects, lights)
        case Some("light") =>
          val Array(x, y, z, r, g, b) = fields.tail.map(_.toFloat)
          readLines(in, objects, Light(Vector(x, y, z), Colour(r, g, b)) :: lights)
        case None =>
          readLines(in, objects, lights)
        case Some(x) =>
          throw new RuntimeException("unknown command: " + x)
      }
    }
  }
}
