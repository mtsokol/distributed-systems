package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import domain.{LibraryAction, Book, Price, BookResult, Search}
import scala.util.Success

object SearchActor {

  def act(replyTo: ActorRef[LibraryAction]): Behavior[Search] = Behaviors.receive {
    (ctx, search) =>

      println(s"searching for ${search.book}")
      replyTo ! BookResult(Success(Book(search.book, Price(23.23))))
      Behaviors.same
  }

}
