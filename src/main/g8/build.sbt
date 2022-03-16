import play.grpc.gen.scaladsl._
import scalapb.GeneratorOption._
import play.sbt.PlayImport.PlayKeys._

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(
    `$name;format="norm"$-protobuf`,
    `$name;format="norm"$-core`,
    `$name;format="norm"$-server`,
    `$name;format="norm"$-db-profile`,
    `$name;format="norm"$-db`
  )
  .settings(
    inThisBuild(
      Seq(
        organization := "$organization$",
        scalaVersion := "$scala_version$"
      )
    )
  )

lazy val `$name;format="norm"$-protobuf` = (project in file("$name;format="norm"$-protobuf"))
  .enablePlugins(
    AkkaGrpcPlugin
  )
  .settings(
    name := "$name;format="norm"$-protobuf",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.protobufDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    Compile / packageBin / mappings ~= { (ms: Seq[(File, String)]) =>
      ms filter {
        case (_, toPath) =>
          toPath.endsWith(".proto")
      }
    },
    Compile / packageBin / packageOptions += Package
      .ManifestAttributes("ScalaPB-Options-Proto" -> "$package;format="packaged"$/grpc/$name;format="snake"$_service_options.proto")
  )

lazy val `$name;format="norm"$-db-profile` = (project in file("$name;format="norm"$-db-profile"))
  .settings(
    name := "$name;format="norm"$-db-profile",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.dbDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.dbDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8"
  )

lazy val `$name;format="norm"$-db` = (project in file("$name;format="norm"$-db"))
  .dependsOn(`$name;format="norm"$-db-profile`)
  .enablePlugins($name;format="space,Camel"$DbPlugin)
  .settings(
    name := "$name;format="norm"$-db",
    $name;format="space,camel"$SlickCodegenAdditionalClasspath += (`$name;format="norm"$-db-profile` / Compile / classDirectory).value,
    $name;format="space,camel"$SlickCodegenAdditionalClasspath ++= (Compile / resourceDirectories).value,
    Compile / sourceGenerators += $name;format="space,camel"$SlickCodegen.taskValue,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8"
  )

lazy val `$name;format="norm"$-core` = (project in file("$name;format="norm"$-core"))
  .dependsOn(`$name;format="norm"$-db`)
  .enablePlugins(AkkaGrpcPlugin)
  .settings(
    name := "$name;format="norm"$-core",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.coreDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.coreDependencyOverrides,
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufDependencies,
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufServiceDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.protobufDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaClientCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client),
    Compile / PB.targets += scalapb.validate
      .gen(FlatPackage) -> (Compile / akkaGrpcCodeGeneratorSettings / target).value
  )

lazy val `$name;format="norm"$-server` = (project in file("$name;format="norm"$-server"))
  .enablePlugins(
    AkkaGrpcPlugin,
    PlayScala,
    PlayAkkaHttp2Support,
    $name;format="space,Camel"$ServerPlugin
  )
  .dependsOn(
    `$name;format="norm"$-core`
  )
  .settings(
    name := "$name;format="norm"$-server",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.serverDependencies,
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.serverDependencyOverrides,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.protobufDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    akkaGrpcCodeGeneratorSettings += "server_power_apis",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaServerCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    Compile / PB.protoSources += (`$name;format="norm"$-protobuf` / sourceDirectory).value / "main" / "protobuf",
    Compile / PB.targets += scalapb.validate
      .gen(FlatPackage) -> (Compile / akkaGrpcCodeGeneratorSettings / target).value,
    devSettings ++= Seq(
      "play.server.http.port" -> "$app_port$"
    ),
    Universal / packageName := name.value,
    topLevelDirectory := Some(packageName.value)
  )
