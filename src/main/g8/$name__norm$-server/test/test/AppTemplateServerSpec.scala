package test

import $package$.grpc.{EchoRequest, $name;format="space,Camel"$ServiceClient}
import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.grpc.GrpcClientSettings
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import play.api.test.Helpers.await

import java.util.concurrent.TimeUnit
import scala.util.Random

class AppTemplateServerSpec extends PlaySpec with GuiceOneServerPerSuite {

  "verify gRPC is working" in {
    implicit val sys: ActorSystem = app.injector.instanceOf[ActorSystem]
    val clientSettings = GrpcClientSettings.connectToServiceAt("127.0.0.1", port).withTls(false)
    val client = $name;format="space,Camel"$ServiceClient(clientSettings)
    val message = Random.nextString(100)
    val response = await(client.echo(EchoRequest(message = message)), 1, TimeUnit.MINUTES)
    response.message mustBe message
  }
}
