package actors

import akka.actor.typed.{ActorRefResolver, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import domain.{Act, Book, BookTitle, Order, Result, Search, ServerMsg}
import scala.util.Success

object ClientActor {

  val actAsBook: Behavior[Act] =
    Behaviors.receive {
      (_, message) =>
      message match {
        case Result(Success(Book(bookTitle, price))) =>

          println(bookTitle, price)

          act
      }


    }

  val act: Behavior[Act] = Behaviors.receive {
    (ctx, message) =>
      message match {
        case Search(book) =>


          val a = ActorRefResolver(ctx.system)

          val y = a.resolveActorRef[ServerMsg]("akka.tcp://server@127.0.0.1:2552/user")

          y ! ServerMsg(ctx.self, Search(book))

          actAsBook

        case Order(xd) =>

          val a = ActorRefResolver(ctx.system)

          val b = a.toSerializationFormat(ctx.self)

          println(b)

          actAsBook

      }
  }

}
