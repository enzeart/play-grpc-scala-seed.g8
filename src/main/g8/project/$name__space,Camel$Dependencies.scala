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
  val playGrpcRuntime: ModuleID = "org.playframework" %% "play-grpc-runtime" % "$play_grpc_runtime_version$"

  val scalapbRuntime: ModuleID = "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion

  val scalapbRuntimeProtobuf: ModuleID = scalapbRuntime % ProtobufConfig

  // Pekko
  val pekkoDiscovery: ModuleID = "org.apache.pekko" %% "pekko-discovery" % PlayVersion.pekkoVersion

  val pekkoHttp: ModuleID = "org.apache.pekko" %% "pekko-http" % PlayVersion.pekkoHttpVersion

  val pekkoHttpSprayJson: ModuleID = "org.apache.pekko" %% "pekko-http-spray-json" % PlayVersion.pekkoHttpVersion

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

  val flywayDatabasePostgresql: ModuleID = "org.flywaydb" % "flyway-database-postgresql" % "$flyway_version$" % Test

  val protobufDependencies: Seq[ModuleID] = Seq(
    playGrpcRuntime,
    scalapbRuntimeProtobuf
  )

  val protobufDependencyOverrides: Seq[ModuleID] = Seq[ModuleID](
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
    flywayCore,
    flywayDatabasePostgresql
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
    pekkoDiscovery,
    pekkoHttp,
    pekkoHttpSprayJson
  )
}
