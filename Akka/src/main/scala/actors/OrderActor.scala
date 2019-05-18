package actors

import java.io.{FileWriter, PrintWriter}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import domain.{Order, OrderCompleted, OrderFailed, ServerMsg}
import scala.util.{Failure, Success, Try}

object OrderActor {

  def act: Behavior[ServerMsg] = Behaviors.receiveMessage {
    case ServerMsg(replyTo, order@Order(_)) =>

      println(s"Ordering ${order.book}")

      Try {
        val pw = new PrintWriter(new FileWriter("src/main/resources/db/orders.txt", true))
        pw.write(order.book.title + "\n")
        pw.close()
      } match {
        case Success(_) =>
          println(s"Ordered ${order.book}")
          replyTo ! OrderCompleted
        case Failure(_) =>
          println(s"Failed ordering ${order.book}")

          replyTo ! OrderFailed
      }

      Behaviors.same

    case _ =>
      Behaviors.unhandled
  }

}
