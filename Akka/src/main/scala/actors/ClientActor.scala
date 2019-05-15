package actors

import akka.actor.typed.{ActorRefResolver, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import domain.{Book, BookResult, LibraryAction, Order, OrderCompleted, Search, ServerMsg}

import scala.util.{Failure, Success}


object ClientActor {

  def actAsSearch: Behavior[LibraryAction] =
    Behaviors.receiveMessage {
      case BookResult(Success(Book(bookTitle, price))) =>
        println(bookTitle, price)
        act

      case BookResult(Failure(exception)) =>
        println(exception)
        act
      case _ =>
        Behaviors.unhandled
    }

  def actAsOrder: Behavior[LibraryAction] = Behaviors.receiveMessage {
    case OrderCompleted =>
      println("Order completed")
      act

    case _ =>
      Behaviors.unhandled
  }

  def actAsStream: Behavior[LibraryAction] =
    Behaviors.receiveMessage {
      case _ =>
        act
    }

  def act: Behavior[LibraryAction] = Behaviors.receive {
    (ctx, message) =>
      message match {
        case Search(book) =>


          val a = ActorRefResolver(ctx.system)

          val y = a.resolveActorRef[ServerMsg]("akka.tcp://server@127.0.0.1:2552/user")

          y ! ServerMsg(ctx.self, Search(book))

          actAsSearch

        case Order(xd) =>

          val a = ActorRefResolver(ctx.system)

          val b = a.toSerializationFormat(ctx.self)

          println(b)

          actAsOrder

        case _ =>

          Behaviors.same
      }
  }

}
