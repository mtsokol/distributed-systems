package actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import domain.{Order, OrderCompleted, ServerMsg}

object OrderActor {

  def act: Behavior[ServerMsg] = Behaviors.receive {
    (ctx, message) =>
      message match {
        case ServerMsg(replyTo, o@Order(_)) =>

          println(s"Ordering ${o.book}")
          replyTo ! OrderCompleted

          Behaviors.same

        case _ =>
          Behaviors.unhandled
      }
  }

}
