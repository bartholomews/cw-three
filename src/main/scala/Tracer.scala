import java.io.File

import com.mildlyskilled.Coordinator
import com.mildlyskilled.{Scene, Image, Coordinator, Trace}


object Tracer extends App {

  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = Scene.fromFile(infile)
  val t = new Trace
  render(scene, outfile, t.Width, t.Height)

  println("rays cast " + t.rayCount)
  println("rays hit " + t.hitCount)
  println("light " + t.lightCount)
  println("dark " + t.darkCount)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    val image = new Image(width, height)

    // Init the coordinator -- must be done before starting it.
    Coordinator.init(image, outfile)

    // TODO: Start the Coordinator actor.

    scene.traceImage(width, height)

    // AJ - moved print to Coordinator actor
    //Coordinator.print
  }

}
