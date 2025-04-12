package routers

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.grpc.scaladsl.Metadata
import $package$.grpc.{Abstract$name;format="space,Camel"$ServicePowerApiRouter, EchoReply, EchoRequest}
import config.AppServerConfig
import play.api.Logging

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class $name;format="space,Camel"$ServiceRouter @Inject() (appServerConfig: AppServerConfig)(
    implicit actorSystem: ActorSystem,
) extends Abstract$name;format="space,Camel"$ServicePowerApiRouter(actorSystem, GrpcRouterExceptionHandler)
    with Logging {

  override def echo(in: EchoRequest, metadata: Metadata): Future[EchoReply] = {
    Future.successful(EchoReply(message = in.message))
  }
}
