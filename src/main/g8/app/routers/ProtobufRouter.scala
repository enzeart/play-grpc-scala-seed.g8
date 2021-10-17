package routers

import akka.actor.ActorSystem
import $organization$.{AbstractAppServiceRouter, HelloReply, HelloRequest}

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class ProtobufRouter @Inject()(implicit actorSystem: ActorSystem) extends AbstractAppServiceRouter(actorSystem) {

  override def sayHello(in: HelloRequest): Future[HelloReply] = Future.successful(HelloReply(message = in.name))

}
