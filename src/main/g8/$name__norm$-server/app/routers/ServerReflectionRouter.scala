package routers

import $package$.grpc.$name;format="space,Camel"$Service
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.model.{HttpRequest, HttpResponse}
import play.grpc.internal.PlayRouter

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future

@Singleton
class ServerReflectionRouter @Inject() ()(
  implicit actorSystem: ActorSystem,
) extends PlayRouter("grpc.reflection.v1alpha.ServerReflection/ServerReflectionInfo") {

  private val serviceDescriptions = List(
    $name;format="space,Camel"$Service
  )

  override protected val respond: HttpRequest => Future[HttpResponse] =
    org.apache.pekko.grpc.scaladsl.ServerReflection(serviceDescriptions)(actorSystem)
}
