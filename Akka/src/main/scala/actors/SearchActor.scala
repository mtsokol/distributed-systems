package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import domain.{Book, BookResult, LibraryAction, Price, Search}
import scala.util.{Failure, Success}

object SearchActor {

  sealed trait Db

  case object DbOne extends Db

  case object DbTwo extends Db

  def act(replyTo: ActorRef[LibraryAction]): Behavior[LibraryAction] = Behaviors.receive {
    (ctx, action) =>
      action match {
        case search@Search(_) =>

          val a = ctx.spawn(searchDb(ctx.self, DbOne), "db1")
          val b = ctx.spawn(searchDb(ctx.self, DbTwo), "db2")

          a ! search
          b ! search

          println(s"searching for ${search.book}")

          waiting(replyTo)

        case _ =>
          Behaviors.unhandled
      }
  }

  def waiting(replyTo: ActorRef[LibraryAction], failed: Int = 0): Behavior[LibraryAction] = Behaviors.receiveMessage {
    case fail@BookResult(Failure(_)) =>
      failed match {
        case 0 =>
          waiting(replyTo, failed = 1)
        case 1 =>
          replyTo ! fail
          Behaviors.stopped
      }

    case book@BookResult(Success(_)) =>
      replyTo ! book
      Behaviors.stopped

    case _ =>
      Behaviors.unhandled
  }

  private def searchDb(replyTo: ActorRef[LibraryAction], db: Db): Behavior[Search] = Behaviors.receiveMessage {
    search =>

      //TODO searching
      replyTo ! BookResult(Success(Book(search.book, Price(23.23))))

      Behaviors.stopped
  }

}
