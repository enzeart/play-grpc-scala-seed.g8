package routers

import akka.actor.ActorSystem
import akka.grpc.scaladsl.Metadata
import $package$.grpc.{Abstract$name;format="space,Camel"$ServicePowerApiRouter, EchoReply, EchoRequest}
import config.AppConfig

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class ProtobufRouter @Inject() (appConfig: AppConfig)(implicit actorSystem: ActorSystem)
    extends Abstract$name;format="space,Camel"$ServicePowerApiRouter(actorSystem) {

  override def echo(in: EchoRequest, metadata: Metadata): Future[EchoReply] =
    Future.successful(EchoReply(message = in.message))
}
