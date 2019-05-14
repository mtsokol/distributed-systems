import actors.ClientActor
import akka.actor.typed.ActorSystem
import com.typesafe.config.ConfigFactory
import domain.{Act, BookTitle, Order, Search, StreamContent}
import scala.io.StdIn.readLine

object Client extends App {

  val config = ConfigFactory.load()

  val system: ActorSystem[Act] = ActorSystem(ClientActor.act, "client", config.getConfig("ClientApp"))

  while (true) {

    val input: List[String] = readLine.split(" ").toList

    input match {
      case "s" :: title :: _ =>
        system ! Search(BookTitle(title))
      case "o" :: title :: _ =>
        system ! Order(BookTitle(title))
      case "c" :: title :: _ =>
        system ! StreamContent(BookTitle(title))
      case _ =>
        println("Invalid command")
    }
  }

}
