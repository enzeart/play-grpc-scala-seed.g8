package routers

import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.grpc.ServiceDescription
import org.apache.pekko.http.scaladsl.model.{HttpRequest, HttpResponse}
import play.grpc.internal.PlayRouter

import javax.inject.{Inject, Singleton}
import scala.concurrent.Future
import scala.jdk.CollectionConverters.CollectionHasAsScala

abstract class ServerReflectionRouter(serviceName: String, serviceDescriptions: List[ServiceDescription])(implicit
    actorSystem: ActorSystem
) extends PlayRouter(serviceName) {

  def this(serviceName: String, serviceDescriptions: java.util.Set[ServiceDescription])(implicit
      actorSystem: ActorSystem
  ) = {
    this(serviceName, serviceDescriptions.asScala.toList)
  }

  override protected val respond: HttpRequest => Future[HttpResponse] =
    org.apache.pekko.grpc.scaladsl.ServerReflection(serviceDescriptions)(actorSystem)
}

@Singleton
class V1AlphaServerReflectionRouter @Inject() (serviceDescriptions: java.util.Set[ServiceDescription])(implicit
    actorSystem: ActorSystem
) extends ServerReflectionRouter("grpc.reflection.v1alpha.ServerReflection/ServerReflectionInfo", serviceDescriptions)

@Singleton
class V1ServerReflectionRouter @Inject() (serviceDescriptions: java.util.Set[ServiceDescription])(implicit
    actorSystem: ActorSystem
) extends ServerReflectionRouter("grpc.reflection.v1.ServerReflection/ServerReflectionInfo", serviceDescriptions)
