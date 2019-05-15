package actors

import akka.NotUsed
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.typed.scaladsl.{ActorMaterializer, ActorSink}
import domain.{LibraryAction, StreamCompleted, StreamFailed, StreamRequest, StreamResult}

object StreamContentActor {

  def act(replyTo: ActorRef[LibraryAction]): Behavior[StreamRequest] = Behaviors.receive {
    (ctx, streamRequest) =>

      val sink: Sink[LibraryAction, NotUsed] =
        ActorSink.actorRef[LibraryAction](replyTo, StreamCompleted, StreamFailed)

      implicit val system = ctx.system
      implicit val materializer = ActorMaterializer()

      Source(StreamResult("number") :: StreamResult("five") :: Nil).runWith(sink)

      Behaviors.same
  }


}
