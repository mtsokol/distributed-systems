import actors.ClientActor
import akka.actor.typed.ActorSystem
import com.typesafe.config.ConfigFactory
import domain.{LibraryAction, BookTitle, Order, Search, StreamRequest}
import scala.io.StdIn.readLine

object Client extends App {

  val config = ConfigFactory.load()

  val system: ActorSystem[LibraryAction] = ActorSystem(ClientActor.main, "client", config.getConfig("ClientApp"))

  while (true) {

    val input: List[String] = readLine.split(" ").toList

    input match {
      case "s" :: title :: _ =>
        system ! Search(BookTitle(title))
      case "o" :: title :: _ =>
        system ! Order(BookTitle(title))
      case "c" :: title :: _ =>
        system ! StreamRequest(BookTitle(title))
      case _ =>
        println("Invalid command")
    }
  }

}
