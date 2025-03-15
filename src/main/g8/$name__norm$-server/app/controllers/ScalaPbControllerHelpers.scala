package controllers

import controllers.ScalaPbControllerHelpers._
import org.apache.pekko.grpc.scaladsl.{Metadata, MetadataBuilder}
import org.apache.pekko.util.ByteString
import play.api.http.{ContentTypeOf, ContentTypes, Writeable}
import play.api.mvc.{Action, BaseController, BodyParser, PlayBodyParsers, Request, Results}
import scalapb.json4s.JsonFormat
import scalapb.{GeneratedMessage, GeneratedMessageCompanion}

import scala.concurrent.{ExecutionContext, Future}

object ScalaPbControllerHelpers {

  implicit class ExtendedPlayBodyParsers(parse: PlayBodyParsers)(implicit ec: ExecutionContext) {

    def scalapb[A <: GeneratedMessage: GeneratedMessageCompanion]: BodyParser[A] = parse.when(
      _.contentType.exists(m => m.equalsIgnoreCase("text/json") || m.equalsIgnoreCase("application/json")),
      parse.tolerantText.map(JsonFormat.fromJsonString[A]),
      _ => Future.successful(Results.UnsupportedMediaType("Expecting text/json or application/json body"))
    )
  }

  implicit def contentTypeOfScalaPb: ContentTypeOf[GeneratedMessage] =
    ContentTypeOf[GeneratedMessage](Some(ContentTypes.JSON))

  implicit def writeableOfScalaPb: Writeable[GeneratedMessage] =
    Writeable(a => ByteString.fromString(JsonFormat.toJsonString(a)))
}

trait ScalaPbControllerHelpers { self: BaseController =>

  protected def PekkoGrpcAction[A <: GeneratedMessage: GeneratedMessageCompanion, B <: GeneratedMessage](
      rpc: (A, Metadata) => Future[B],
      metadata: Request[A] => Metadata = (_: Request[A]) => MetadataBuilder.empty
  )(implicit ec: ExecutionContext): Action[A] =
    Action.async(parse.scalapb[A])(request => rpc(request.body, metadata(request)).map(Ok(_)))
}
