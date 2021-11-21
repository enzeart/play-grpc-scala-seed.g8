import $name;format="space,Camel"$Dependencies._
import play.grpc.gen.scaladsl.PlayScalaServerCodeGenerator

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(
    `$name;format="norm"$-proto`,
    `$name;format="norm"$-server`
  )
  .settings(
    inThisBuild(Seq(
      organization := "$organization$",
      scalaVersion := "$scala_version$"
    ))
  )

lazy val `$name;format="norm"$-proto` = (project in file("$name;format="norm"$-proto"))
  .enablePlugins(
    AkkaGrpcPlugin,
    $name;format="space,Camel"$Plugin
  )
  .settings(
    name := "$name;format="norm"$-proto",
    libraryDependencies := protoDependencies,
    akkaGrpcCodeGeneratorSettings += "server_power_apis",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaServerCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server),
    Compile / packageBin / mappings ~= { (ms: Seq[(File, String)]) =>
      ms filter {
        case (_, toPath) =>
          toPath.endsWith(".proto")
      }
    }
  )

lazy val `$name;format="norm"$-server` = (project in file("$name;format="norm"$-server"))
  .enablePlugins(
    PlayScala,
    PlayAkkaHttp2Support,
    $name;format="space,Camel"$Plugin
  )
  .dependsOn(`$name;format="norm"$-proto`)
  .settings(
    name := "$name;format="norm"$-server",
    libraryDependencies ++= serverDependencies,
    dependencyOverrides ++= serverDependencyOverrides
  )
