import play.core.PlayVersion
import play.sbt.PlayImport._
import sbt._
import sbtprotoc.ProtocPlugin.{ProtobufConfig, ProtobufSrcConfig}

object $name;format="space,Camel"$Dependencies {

  //  Utilities
  val pureconfig: ModuleID = "com.github.pureconfig" %% "pureconfig" % "$pureconfig_version$"

  val scalaGuice: ModuleID = "net.codingwell" %% "scala-guice" % "$scala_guice_version$"

  //  Play Framework
  val scalatestplusPlay: ModuleID = "org.scalatestplus.play" %% "scalatestplus-play" % "$scalatestplus_play_version$" % Test

  //  Protobuf
  val playGrpcRuntime: ModuleID = "com.lightbend.play" %% "play-grpc-runtime" % "$play_grpc_runtime_version$"

  val scalapbLenses: ModuleID = "com.thesamet.scalapb" %% "lenses" % "$scalapb_lenses_version$"

  val scalapbRuntime: ModuleID = "com.thesamet.scalapb" %% "scalapb-runtime" % "$scalapb_runtime_version$"

  val scalapbValidateCore: ModuleID =
    "com.thesamet.scalapb" %% "scalapb-validate-core" % scalapb.validate.compiler.BuildInfo.version

  val scalapbValidateCoreProtobuf: ModuleID = scalapbValidateCore % ProtobufConfig

  // Akka
  val akkaDiscovery: ModuleID = "com.typesafe.akka" %% "akka-discovery" % PlayVersion.akkaVersion

  val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % PlayVersion.akkaHttpVersion

  val akkaHttpSprayJson: ModuleID = "com.typesafe.akka" %% "akka-http-spray-json" % PlayVersion.akkaHttpVersion

  // Database
  val slickVersion = "$slick_version$"

  val slick: ModuleID = "com.typesafe.slick" %% "slick" % slickVersion

  val slickCodegen: ModuleID = "com.typesafe.slick" %% "slick-codegen" % slickVersion

  val postgresql: ModuleID = "org.postgresql" % "postgresql" % "$postgresql_version$"

  val slickPg: ModuleID = "com.github.tminglei" %% "slick-pg" % "$slick_pg_version$"

  val slickHikaricp: ModuleID = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion

  // Test
  val testcontainersScalaVersion = "$testcontainers_scala_version$"

  val scalatest: ModuleID = "org.scalatest" %% "scalatest" % "$scalatest_version$" % Test

  val testcontainersScalaScalatest = "com.dimafeng" %% "testcontainers-scala-scalatest" % testcontainersScalaVersion % Test

  val testcontainersScalaPostgresql = "com.dimafeng" %% "testcontainers-scala-postgresql" % testcontainersScalaVersion % Test

  val flywayCore: ModuleID = "org.flywaydb" % "flyway-core" % "$flyway_version$" % Test

  val protobufDependencies: Seq[ModuleID] = Seq(
    playGrpcRuntime,
    scalapbValidateCore,
    scalapbValidateCoreProtobuf
  )

  val protobufDependencyOverrides: Seq[ModuleID] = Seq(
    scalapbLenses,
    scalapbRuntime
  )

  val protobufServiceDependencies: Seq[ModuleID] = Seq[ModuleID](

  ).map(_  % ProtobufSrcConfig intransitive())

  val dbDependencies: Seq[ModuleID] = Seq(
    slick,
    slickCodegen,
    postgresql,
    slickPg
  )

  val dbDependencyOverrides: Seq[ModuleID] = Seq(
  )

  val coreDependencies: Seq[ModuleID] = Seq(
    slickHikaricp % Test,
    scalatest,
    testcontainersScalaScalatest,
    testcontainersScalaPostgresql,
    flywayCore
  )

  val coreDependencyOverrides: Seq[ModuleID] = Seq(

  )

  val serverDependencies: Seq[ModuleID] = Seq(
    guice,
    pureconfig,
    scalaGuice,
    scalatestplusPlay,
    slickHikaricp
  )

  val serverDependencyOverrides: Seq[ModuleID] = Seq(
    akkaDiscovery,
    akkaHttp,
    akkaHttpSprayJson
  )
}
