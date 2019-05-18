package actors

import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}
import akka.actor.typed.scaladsl.Behaviors
import domain._
import scala.util.Random

object ServerActor {

  def act(orderActor: ActorRef[ServerMsg]): Behavior[ServerMsg] = Behaviors.receive {

    (ctx, message) =>
      val replyTo = message.replyTo
      message.action match {
        case search@Search(_) =>

          val searchActor = ctx.spawn(SearchActor.act(replyTo), s"search-actor-${Random.nextInt(100)}")
          searchActor ! search

          Behaviors.same

        case Order(_) =>

          orderActor ! message

          Behaviors.same

        case streamRequest@StreamRequest(_) =>

          val streamActorBehav = Behaviors.supervise(StreamContentActor.act(replyTo)).onFailure(SupervisorStrategy.stop)

          val streamContentActor = ctx.spawn(streamActorBehav, s"stream-content-${Random.nextInt(100)}")
          streamContentActor ! streamRequest

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
