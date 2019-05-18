package domain

import akka.actor.typed.ActorRef
import scala.util.Try

case class BookTitle(title: String) extends AnyVal

case class Price(value: Double) extends AnyVal

case class Book(bookTitle: BookTitle, price: Price)


sealed trait LibraryAction


sealed trait ActionRequest extends LibraryAction

case class Search(book: BookTitle) extends ActionRequest

case class Order(book: BookTitle) extends ActionRequest

case class StreamRequest(book: BookTitle) extends ActionRequest


sealed trait ActionResponse extends LibraryAction

case class BookResult(value: Try[Book]) extends ActionResponse

case object OrderCompleted extends ActionResponse

case object OrderFailed extends ActionResponse

case class StreamResult(content: String) extends ActionResponse

case object StreamCompleted extends ActionResponse

case class StreamFailed(ex: Throwable) extends ActionResponse


case class ServerMsg(replyTo: ActorRef[LibraryAction], action: ActionRequest)

case class Message(ackTo: ActorRef[LibraryAction], msg: LibraryAction) extends LibraryAction

case class Init(ackTo: ActorRef[LibraryAction]) extends LibraryAction

case object AckMessage extends LibraryAction