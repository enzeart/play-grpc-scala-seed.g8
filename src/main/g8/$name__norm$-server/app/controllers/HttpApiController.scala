package controllers

import play.api.mvc._

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
final class HttpApiController @Inject() (val controllerComponents: ControllerComponents)(implicit ec: ExecutionContext)
    extends BaseController {

  def healthCheck: Action[AnyContent] = Action(Ok(""))
}
