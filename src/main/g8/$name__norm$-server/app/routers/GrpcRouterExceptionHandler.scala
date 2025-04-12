package routers

import io.grpc.Status
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.grpc.{GrpcServiceException, Trailers}
import play.libs.exception.ExceptionUtils

object GrpcRouterExceptionHandler extends (ActorSystem => PartialFunction[Throwable, org.apache.pekko.grpc.Trailers]) {

  override def apply(actorSystem: ActorSystem): PartialFunction[Throwable, Trailers] = {
    case grpcException: GrpcServiceException => Trailers(grpcException.status, grpcException.metadata)
    case t => Trailers(Status.INTERNAL.withCause(t).withDescription(ExceptionUtils.getStackTrace(t)))
  }
}
