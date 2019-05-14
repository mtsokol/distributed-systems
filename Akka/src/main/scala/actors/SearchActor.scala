package actors

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}
import domain.{Act, Book, Price, Result, Search}

import scala.util.Success

object SearchActor {

  def act(replyTo: ActorRef[Act]): Behavior[Search] = Behaviors.receive {
    (ctx, message) =>

      println("searching")

      replyTo.tell(Result(Success(Book(message.book, Price(23.23)))))
      Behaviors.same
  }

}
