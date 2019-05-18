package actors

import java.io.{FileWriter, PrintWriter}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import domain.{Order, OrderCompleted, ServerMsg}

object OrderActor {

  def act: Behavior[ServerMsg] = Behaviors.receiveMessage {
    case ServerMsg(replyTo, order@Order(_)) =>

      println(s"Ordering ${order.book}")

      val pw = new PrintWriter(new FileWriter("src/main/resources/db/orders.txt", true))
      pw.write(order.book.title + "\n")
      pw.close()

      replyTo ! OrderCompleted

      Behaviors.same

    case _ =>
      Behaviors.unhandled
  }

}
