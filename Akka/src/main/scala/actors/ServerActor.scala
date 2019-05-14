package actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import domain.{Order, OrderCompleted, Result, Search, ServerMsg, StreamContent, StreamResult}
import scala.util.Success

object ServerActor {

  val act: Behavior[ServerMsg] = Behaviors.receive {

    (context, message) =>
      val replyTo = message.replyTo
      message.action match {
        case Search(book) =>

          val a = context.spawn(SearchActor.act(replyTo), s"search")

          a ! Search(book)

          Behaviors.same

        case Order(book) =>

          replyTo.tell(Result(Success(OrderCompleted)))

          Behaviors.same
        case StreamContent(book) =>

          replyTo.tell(Result(Success(StreamResult("xd"))))

          Behaviors.same
      }

  }

}