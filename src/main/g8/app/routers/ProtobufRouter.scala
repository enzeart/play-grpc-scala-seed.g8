package routers

import akka.actor.ActorSystem
import akka.grpc.scaladsl.Metadata
import com.example.grpc.{Abstract$name;format="space,Camel"$AppServicePowerApiRouter, EchoReply, EchoRequest}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class ProtobufRouter @Inject()(implicit actorSystem: ActorSystem)
  extends Abstract$name;format="space,Camel"$AppServicePowerApiRouter(actorSystem) {

  override def echo(in: EchoRequest, metadata: Metadata): Future[EchoReply] =
    Future.successful(EchoReply(message = in.message))
}

