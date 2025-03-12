package controllers

import $package$.grpc.EchoRequest
import play.api.mvc._
import routers.$name;format="space,Camel"$ServiceRouter

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
final class HttpApiController @Inject() (
    router: $name;format="space,Camel"$ServiceRouter,
    val controllerComponents: ControllerComponents
)(implicit ec: ExecutionContext)
    extends BaseController
    with ScalaPbControllerHelpers {

  def healthCheck: Action[AnyContent] = Action(Ok(""))

  def echo: Action[EchoRequest] = PekkoGrpcAction(router.echo)
}
