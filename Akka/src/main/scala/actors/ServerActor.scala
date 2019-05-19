package actors

import java.io.FileNotFoundException
import akka.actor.typed.{ActorRef, Behavior, SupervisorStrategy}
import akka.actor.typed.scaladsl.Behaviors
import domain._
import scala.concurrent.duration._
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
      val orderBehav = Behaviors.supervise(OrderActor.act)
        .onFailure[FileNotFoundException](SupervisorStrategy.restart.withLimit(10, 10.seconds))
      val orderActor = ctx.spawn(orderBehav, "order-actor")

      act(orderActor)
    }
  }
}
