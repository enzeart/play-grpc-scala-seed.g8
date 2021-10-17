import play.core.PlayVersion
import sbt._

object PlayGrpcTestDependencies {

  val playGrpcRuntime: ModuleID = "com.lightbend.play" %% "play-grpc-runtime" % "0.9.1"

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
