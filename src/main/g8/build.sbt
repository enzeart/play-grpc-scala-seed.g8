import $name;format="space,Camel"$Dependencies._
import play.grpc.gen.scaladsl.PlayScalaServerCodeGenerator
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
    libraryDependencies ++= protobufDependencies,
    dependencyOverrides ++= protobufDependencyOverrides,
    akkaGrpcCodeGeneratorSettings += "server_power_apis",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaServerCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    Compile / packageBin / mappings ~= { (ms: Seq[(File, String)]) =>
      ms filter {
        case (_, toPath) =>
          toPath.endsWith(".proto")
      }
    },
    Compile / PB.targets += scalapb.validate.gen(FlatPackage) -> (Compile / akkaGrpcCodeGeneratorSettings / target).value,
    Compile / packageBin / packageOptions += Package.ManifestAttributes("ScalaPB-Options-Proto" -> "$package;format="packaged"$/grpc/$name;format="norm"$-service-options.proto")
  )

lazy val `$name;format="norm"$-server` = (project in file("$name;format="norm"$-server"))
  .enablePlugins(
    PlayScala,
    PlayAkkaHttp2Support,
    $name;format="space,Camel"$ServerPlugin
  )
  .dependsOn(`$name;format="norm"$-protobuf`)
  .settings(
    name := "$name;format="norm"$-server",
    libraryDependencies ++= serverDependencies,
    dependencyOverrides ++= serverDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    devSettings ++= Seq(
      "play.server.http.port" -> "$app_port$"
    )
  )
