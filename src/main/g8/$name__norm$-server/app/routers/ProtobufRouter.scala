package routers

import akka.actor.ActorSystem
import akka.grpc.scaladsl.Metadata
import $package$.grpc.{Abstract$name;format="space,Camel"$ServicePowerApiRouter, EchoReply, EchoRequest}
import $package$.core.$name;format="space,Camel"$Service
import config.AppConfig

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProtobufRouter @Inject() (appConfig: AppConfig, $name;format="space,camel"$Service: $name;format="space,Camel"$Service)(
    implicit actorSystem: ActorSystem,
    executionContext: ExecutionContext
) extends Abstract$name;format="space,Camel"$ServicePowerApiRouter(actorSystem) {

  override def echo(in: EchoRequest, metadata: Metadata): Future[EchoReply] =
    Future.successful(EchoReply(message = in.message))
}
