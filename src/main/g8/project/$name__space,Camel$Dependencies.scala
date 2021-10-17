import play.core.PlayVersion
import sbt._

object PlayGrpcTestDependencies {

  val playGrpcRuntime: ModuleID = "com.lightbend.play" %% "play-grpc-runtime" % "$play_grpc_runtime_version$"

  val scalatestplusPlay: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "$scalatestplus_play_version$" % Test

  val akkaDiscovery: ModuleID = "com.typesafe.akka" %% "akka-discovery" % PlayVersion.akkaVersion

  val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % PlayVersion.akkaHttpVersion

  val akkaHttpSprayJson: ModuleID = "com.typesafe.akka" %% "akka-http-spray-json" % PlayVersion.akkaHttpVersion

  val rootDependencies: Seq[ModuleID] = Seq(
    playGrpcRuntime
  )

  val overrideDependencies: Seq[ModuleID] = Seq(
    akkaDiscovery,
    akkaHttp,
    akkaHttpSprayJson
  )
}
