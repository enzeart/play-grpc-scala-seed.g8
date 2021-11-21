import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt._

object $name;format="space,Camel"$Dependencies {

  val playGrpcRuntime: ModuleID = "com.lightbend.play" %% "play-grpc-runtime" % "$play_grpc_runtime_version$"

  val scalaGuice: ModuleID = "net.codingwell" %% "scala-guice" % "$scala_guice_version$"

  val scalatestplusPlay: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "$scalatestplus_play_version$" % Test

  val akkaDiscovery: ModuleID = "com.typesafe.akka" %% "akka-discovery" % PlayVersion.akkaVersion

  val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % PlayVersion.akkaHttpVersion

  val akkaHttpSprayJson: ModuleID = "com.typesafe.akka" %% "akka-http-spray-json" % PlayVersion.akkaHttpVersion

  val pureconfig: ModuleID = "com.github.pureconfig" %% "pureconfig" % "$pureconfig_version$"

  val protoDependencies: Seq[ModuleID] = Seq(
    playGrpcRuntime
  )

  val serverDependencies: Seq[ModuleID] = Seq(
    guice,
    scalaGuice,
    scalatestplusPlay,
    pureconfig
  )

  val serverDependencyOverrides: Seq[ModuleID] = Seq(
    akkaDiscovery,
    akkaHttp,
    akkaHttpSprayJson
  )
}
