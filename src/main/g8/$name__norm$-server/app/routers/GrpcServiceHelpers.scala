package routers

import org.apache.pekko.grpc.GrpcServiceException
import io.grpc.Status
import play.api.Logger
import play.libs.exception.ExceptionUtils
import routers.GrpcServiceHelpers.ExtendedThrowable

import scala.concurrent.{ExecutionContext, Future}

object GrpcServiceHelpers {

  implicit class ExtendedThrowable(t: Throwable) {

    def toGrpcServiceException: GrpcServiceException = {
      val description = ExceptionUtils.getStackTrace(t)
      new GrpcServiceException(Status.INTERNAL.withCause(t).withDescription(description))
    }
  }
}

trait GrpcServiceHelpers {

  protected val logger: Logger
  implicit val executionContext: ExecutionContext

  def withGrpcServiceExceptionHandling[A](f: => Future[A]): Future[A] =
    Future(f).flatten.recoverWith { case t: Throwable =>
      logger.error("Internal Error", t)
      Future.failed(t.toGrpcServiceException)
    }
}
