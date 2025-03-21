package routers

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.grpc.scaladsl.Metadata
import $package$.grpc.{Abstract$name;format="space,Camel"$ServicePowerApiRouter, EchoReply, EchoRequest}
import config.AppServerConfig
import play.api.Logging

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class $name;format="space,Camel"$ServiceRouter @Inject() (appServerConfig: AppServerConfig)(
    implicit actorSystem: ActorSystem,
    override val executionContext: ExecutionContext
) extends Abstract$name;format="space,Camel"$ServicePowerApiRouter(actorSystem)
    with GrpcServiceHelpers
    with Logging {

  override def echo(in: EchoRequest, metadata: Metadata): Future[EchoReply] = withGrpcServiceExceptionHandling {
    Future.successful(EchoReply(message = in.message))
  }
}
