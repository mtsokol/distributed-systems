package actors

import akka.actor.typed.{ActorRef, ActorRefResolver, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import domain._
import scala.util.{Failure, Success}

object ClientActor {

  def actAsSearch(baseAct: Behavior[LibraryAction]): Behavior[LibraryAction] =
    Behaviors.receiveMessage {
      case BookResult(Success(book@Book(_, _))) =>
        println(book)
        baseAct

      case BookResult(Failure(exception)) =>
        println(exception)
        baseAct

      case _ =>
        Behaviors.unhandled
    }

  def actAsOrder(baseAct: Behavior[LibraryAction]): Behavior[LibraryAction] =
    Behaviors.receiveMessage {
      case ord@OrderCompleted =>
        println(ord)
        baseAct

      case _ =>
        Behaviors.unhandled
    }

  def actAsStream(baseAct: Behavior[LibraryAction]): Behavior[LibraryAction] =
    Behaviors.receiveMessage {
      case Init(replyTo) =>
        replyTo ! AckMessage
        Behaviors.same

      case Message(replyTo, content) =>
        println(content)
        Thread.sleep(1000)
        replyTo ! AckMessage
        Behaviors.same

      case completed@StreamCompleted =>
        println(completed)
        baseAct

      case StreamFailed(ex) =>
        println(ex)
        baseAct

      case _ =>
        Behaviors.unhandled
    }

  def act(server: ActorRef[ServerMsg]): Behavior[LibraryAction] = Behaviors.receive {
    (ctx, message) =>
      message match {
        case s@Search(_) =>

          server ! ServerMsg(ctx.self, s)
          actAsSearch(act(server))

        case o@Order(_) =>

          server ! ServerMsg(ctx.self, o)
          actAsOrder(act(server))

        case s@StreamRequest(_) =>

          server ! ServerMsg(ctx.self, s)
          actAsStream(act(server))

        case _ =>
          Behaviors.unhandled
      }
  }

  def main: Behavior[LibraryAction] = Behaviors.setup {
    ctx =>
      val resolver = ActorRefResolver(ctx.system)
      val server = resolver.resolveActorRef[ServerMsg]("akka.tcp://server@127.0.0.1:2552/user")
      act(server)
  }

}
