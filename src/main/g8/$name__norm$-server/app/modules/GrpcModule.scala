package modules

import com.google.inject.AbstractModule
import com.google.inject.multibindings.ProvidesIntoSet
import $package$.grpc.$name;format="space,Camel"$Service
import net.codingwell.scalaguice.ScalaModule
import org.apache.pekko.grpc.ServiceDescription

import javax.inject.Singleton

class GrpcModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = ()

  @Singleton
  @ProvidesIntoSet
  def provide$name;format="space,Camel"$ServiceDescription(): ServiceDescription =
    $name;format="space,Camel"$Service
}
