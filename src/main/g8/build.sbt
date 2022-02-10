import play.grpc.gen.scaladsl._
import scalapb.GeneratorOption._
import play.sbt.PlayImport.PlayKeys._

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(
    `$name;format="norm"$-protobuf`,
    `$name;format="norm"$-server`
  )
  .settings(
    inThisBuild(Seq(
      organization := "$organization$",
      scalaVersion := "$scala_version$"
    ))
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
    akkaGrpcCodeGeneratorSettings += "server_power_apis",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaServerCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    Compile / packageBin / mappings ~= { (ms: Seq[(File, String)]) =>
      ms filter {
        case (_, toPath) =>
          toPath.endsWith(".proto")
      }
    },
    Compile / PB.targets += scalapb.validate.gen(FlatPackage) -> (Compile / akkaGrpcCodeGeneratorSettings / target).value,
    Compile / packageBin / packageOptions += Package.ManifestAttributes("ScalaPB-Options-Proto" -> "$package;format="packaged"$/grpc/$name;format="norm"$-service-options.proto")
  )

lazy val `$name;format="norm"$-core` = (project in file("$name;format="norm"$-core"))
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
    Compile / PB.targets += scalapb.validate.gen(FlatPackage) -> (Compile / akkaGrpcCodeGeneratorSettings / target).value
  )


lazy val `$name;format="norm"$-server` = (project in file("$name;format="norm"$-server"))
  .enablePlugins(
    PlayScala,
    PlayAkkaHttp2Support,
    $name;format="space,Camel"$ServerPlugin
  )
  .dependsOn(
    `$name;format="norm"$-protobuf`,
    `$name;format="norm"$-core`
  )
  .settings(
    name := "$name;format="norm"$-server",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.serverDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.serverDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    devSettings ++= Seq(
      "play.server.http.port" -> "$app_port$"
    )
  )
