package actors

import akka.NotUsed
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.typed.scaladsl.{ActorMaterializer, ActorSink}
import domain.{LibraryAction, Order, OrderCompleted, Search, ServerMsg, StreamCompleted, StreamContent, StreamFailed, StreamResult}

object ServerActor {

  def act: Behavior[ServerMsg] = Behaviors.receive {

    (context, message) =>
      val replyTo = message.replyTo
      message.action match {
        case Search(book) =>

          val a = context.spawn(SearchActor.act(replyTo), s"search")

          a ! Search(book)

          Behaviors.same

        case Order(book) =>

          replyTo.tell(OrderCompleted)

          Behaviors.same
        case StreamContent(book) =>

          val source: Source[String, NotUsed] = Source("a" :: "number" :: "of" :: "words" :: Nil)

          val sink: Sink[LibraryAction, NotUsed] =
            ActorSink.actorRef[LibraryAction](replyTo, StreamCompleted, StreamFailed)

          implicit val system = context.system
          implicit val materializer = ActorMaterializer()

          Source(StreamResult("xd") :: Nil).runWith(sink)

          Behaviors.same
      }

  }

}