import akka.actor.{ActorSystem, Props, _}
import com.mildlyskilled.{Coordinator, _}

object Tracer extends App {

  val (inFile, outFile) = ("src/main/resources/input.dat", "output.png")
  val settings = new Settings(800, 600, 10, 4, 0.6f, Colour.black, 10)
  val counter = new Counter
  val camera = new Camera(Vector.origin, 90f)
  val scene = SceneLoader.load(inFile)

  val image = new Image(settings.width, settings.height)

  val system = ActorSystem("RenderSystem")

  val coordinator = system.actorOf(Props(new Coordinator(image, outFile, scene, settings, counter, camera)), name = "coordinator")

  coordinator ! Start
}
