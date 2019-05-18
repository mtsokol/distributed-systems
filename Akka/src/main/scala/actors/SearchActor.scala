package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import domain.{Book, BookResult, BookTitle, LibraryAction, Price, Search}
import scala.io.Source
import scala.util.{Failure, Success}

object SearchActor {

  sealed trait Db {
    val filename: String
  }

  case object DbOne extends Db {
    val filename: String = "src/main/resources/db/books1.txt"
  }

  case object DbTwo extends Db {
    val filename: String = "src/main/resources/db/books2.txt"
  }

  def act(replyTo: ActorRef[LibraryAction]): Behavior[LibraryAction] = Behaviors.receive {
    (ctx, action) =>
      action match {
        case search@Search(_) =>

          val actorSearchDb1 = ctx.spawn(searchDb(ctx.self, DbOne), "db1")
          val actorSearchDb2 = ctx.spawn(searchDb(ctx.self, DbTwo), "db2")

          actorSearchDb1 ! search
          actorSearchDb2 ! search

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
      val bufferedSource = Source.fromFile(db.filename)
      val rawRecord = bufferedSource.getLines.find{s => s.split(" ").head == search.book.title}
        .map(x => x.split(" "))

      bufferedSource.close

      rawRecord match {
        case Some(Array(title, price)) =>
          replyTo ! BookResult(Success(Book(BookTitle(title), Price(price.toDouble))))

        case None =>
          replyTo ! BookResult(Failure(new Exception("xd")))
      }

      Behaviors.stopped
  }

}
