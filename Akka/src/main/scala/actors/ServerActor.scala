package actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import domain._
import scala.util.Random

object ServerActor {

  def act(orderActor: ActorRef[ServerMsg]): Behavior[ServerMsg] = Behaviors.receive {

    (ctx, message) =>
      val replyTo = message.replyTo
      message.action match {
        case s@Search(_) =>

          val searchActor = ctx.spawn(SearchActor.act(replyTo), s"search-actor-${Random.nextInt(100)}")
          searchActor ! s

          Behaviors.same

        case Order(_) =>

          orderActor ! message

          Behaviors.same

        case s@StreamRequest(_) =>

          val streamContentActor = ctx.spawn(StreamContentActor.act(replyTo), s"stream-content-${Random.nextInt(100)}")
          streamContentActor ! s

          Behaviors.same
      }

  }

  def main: Behavior[ServerMsg] = Behaviors.setup {
    ctx => {
      val orderActor = ctx.spawn(OrderActor.act, "order-actor")

      act(orderActor)
    }
  }
}
