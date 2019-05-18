package actors

import akka.NotUsed
import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import akka.stream.scaladsl.{Sink, Source}
import akka.stream.typed.scaladsl.{ActorMaterializer, ActorSink}
import domain.{AckMessage, Init, LibraryAction, Message, StreamCompleted, StreamFailed, StreamRequest, StreamResult}


object StreamContentActor {

  def act(replyTo: ActorRef[LibraryAction]): Behavior[StreamRequest] = Behaviors.receive {
    (ctx, streamRequest) =>

      val sink: Sink[LibraryAction, NotUsed] =
        ActorSink.actorRefWithAck[LibraryAction, LibraryAction, LibraryAction](
          ref = replyTo,
          messageAdapter = Message,
          onInitMessage = Init,
          ackMessage = AckMessage,
          onCompleteMessage = StreamCompleted,
          onFailureMessage = StreamFailed)

      implicit val system = ctx.system
      implicit val materializer = ActorMaterializer()

      val bufferedSource = scala.io.Source.fromFile(s"src/main/resources/db/books/${streamRequest.book.title}")
      val stream = bufferedSource.getLines.map(StreamResult).toStream

      Source(stream).runWith(sink)

      Behaviors.same
  }


}
