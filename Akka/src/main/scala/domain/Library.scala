package domain

import akka.actor.typed.ActorRef
import scala.util.Try

case class BookTitle(title: String) extends AnyVal

case class Price(value: Double) extends AnyVal


sealed trait Act


sealed trait LibraryAction extends Act

case class Search(book: BookTitle) extends LibraryAction

case class Order(book: BookTitle) extends LibraryAction

case class StreamContent(book: BookTitle) extends LibraryAction


case class Result(value: Response.LibraryResponse) extends Act


sealed trait Response

case class Book(bookTitle: BookTitle, price: Price) extends Response

case object OrderCompleted extends Response

case class StreamResult(content: String) extends Response

object Response {
  type LibraryResponse = Try[Response]
}


case class ServerMsg(replyTo: ActorRef[Act], action: LibraryAction)
