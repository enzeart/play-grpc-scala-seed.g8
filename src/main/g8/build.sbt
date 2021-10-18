import $name;format="space,Camel"$Dependencies._
import play.grpc.gen.scaladsl.PlayScalaServerCodeGenerator

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `$name;format="norm"$` = (project in file("."))
  .enablePlugins(
    PlayScala,
    PlayAkkaHttp2Support,
    AkkaGrpcPlugin,
    $name;format="space,Camel"$Plugin
  )
  .settings(
    name := "$name;format="norm"$",
    libraryDependencies ++= rootDependencies,
    inThisBuild(Seq(
      organization := "$organization$",
      scalaVersion := "$scala_version$"
    )),
    dependencyOverrides ++= overrideDependencies,
    akkaGrpcExtraGenerators += PlayScalaServerCodeGenerator,
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server)
  )
