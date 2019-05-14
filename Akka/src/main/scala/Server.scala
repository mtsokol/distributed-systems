import actors.ServerActor
import akka.actor.typed.ActorSystem
import com.typesafe.config.ConfigFactory
import domain.ServerMsg

object Server extends App {

  val config = ConfigFactory.load()

  val system: ActorSystem[ServerMsg] = ActorSystem(ServerActor.act, "server", config.getConfig("ServerApp"))

}
