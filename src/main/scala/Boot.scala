import akka.actor.ActorSystem
import akka.http.scaladsl.server._
import akka.http.scaladsl._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Sink, Source }
import akka.http.scaladsl.model.HttpResponse
import akka.http._
import akka.io._
import akka.http.scaladsl.Http._
import akka.stream.scaladsl.Flow
import akka.util._
import akka.http.scaladsl.model.StatusCode
import scala.concurrent.Future
import Directives._
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.Uri
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.MediaTypes
import akka.http.scaladsl.model._
import HttpMethods._
import akka.http.scaladsl.model.HttpRequest

object BootWithRouting extends App {

  val host = "127.0.0.1"
  val port = 8080

  implicit val system = ActorSystem("my-testing-system")
  implicit val fm = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val serverSource = Http(system).bind(interface = host, port = port)
  serverSource.runForeach { connection => // foreach materializes the source
    println("Accepted new connection from " + connection.remoteAddress)
    connection handleWithSyncHandler requestHandler

  }

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      HttpResponse(
        entity = HttpEntity(MediaTypes.`text/html`,
          "<html><body>Hello world!</body></html>"))

    case HttpRequest(GET, Uri.Path("/ping"), _, _, _)  => HttpResponse(entity = "PONG!")
    case HttpRequest(GET, Uri.Path("/crash"), _, _, _) => sys.error("BOOM!")
    case _: HttpRequest                                => HttpResponse(404, entity = "Unknown resource!")
  }

 
} 