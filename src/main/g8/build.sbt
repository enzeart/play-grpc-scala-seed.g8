import play.grpc.gen.scaladsl._
import play.sbt.PlayImport.PlayKeys._
import $name;format="space,Camel"$Keys._

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val `$name;format="norm"$` = (project in file("."))
  .aggregate(
    `$name;format="norm"$-protobuf`,
    `$name;format="norm"$-core`,
    `$name;format="norm"$-server`,
    `$name;format="norm"$-db-utils`,
    `$name;format="norm"$-db`
  )
  .settings(
    inThisBuild(
      Seq(
        version := "0.0.0",
        organization := "$organization$",
        scalaVersion := "$scala_version$",
        appDevelopmentPostgresqlContainer := PostgreSqlContainerSingleton.Instance,
        $if(codeartifact_support_enabled.truthy)$
        codeArtifactUrl := "$codeartifact_url$"
        $endif$
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

lazy val `$name;format="norm"$-db-utils` = (project in file("$name;format="norm"$-db-utils"))
  .settings(
    name := "$name;format="norm"$-db-utils",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.dbDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.dbDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8"
  )

lazy val `$name;format="norm"$-db` = (project in file("$name;format="norm"$-db"))
  .dependsOn(`$name;format="norm"$-db-utils`)
  .enablePlugins($name;format="space,Camel"$DbPlugin)
  .settings(
    name := "$name;format="norm"$-db",
     Compile / sourceGenerators += Def.task {
      val classpath = Seq.newBuilder[File]
      classpath ++= (Compile / dependencyClasspath).value.files
      classpath ++= (Compile / resourceDirectories).value
      classpath += (`$name;format="norm"$-db-utils` / Compile / classDirectory).value
      SlickSourceGenerator(
        runner = runner.value,
        mainClass = "slick.codegen.SourceCodeGenerator",
        classLoader = $name;format="space,Camel"$DbPlugin.getClass.getClassLoader,
        classpath = classpath.result(),
        profile = "$package$.db_utils.CustomPostgresProfile",
        jdbcDriver = "org.postgresql.Driver",
        outputDir = (Compile / sourceManaged).value,
        pkg = "$package$.db",
        ignoreInvalidDefaults = true,
        codeGeneratorClass = "$package$.db_utils.CustomSourceCodeGenerator",
        outputToMultipleFiles = true,
        log = streams.value.log,
        databaseContainer = (Compile / appDevelopmentPostgresqlContainer).value
      )
    },
    appFlywayMigration := {
      val jdbcUrl = sys.env.getOrElse("APP_$name;format="snake,upper"$_DB_URL", throw new IllegalArgumentException("Missing environment variable: APP_$name;format="snake,upper"$_DB_URL"))
      val username = sys.env.getOrElse("APP_$name;format="snake,upper"$_DB_USER", throw new IllegalArgumentException("Missing environment variable: APP_$name;format="snake,upper"$_DB_USER"))
      val password = sys.env.getOrElse("APP_$name;format="snake,upper"$_DB_PASSWORD", throw new IllegalArgumentException("Missing environment variable: APP_$name;format="snake,upper"$_DB_PASSWORD"))
      val classpath = Seq.newBuilder[File]
      classpath ++= (Compile / dependencyClasspath).value.files
      classpath ++= (Compile / resourceDirectories).value
      classpath += (`$name;format="norm"$-db-utils` / Compile / classDirectory).value
      FlywayMigration($name;format="space,Camel"$DbPlugin.getClass.getClassLoader, classpath.result(), jdbcUrl, username, password)
    },
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8"
  )

lazy val `$name;format="norm"$-core` = (project in file("$name;format="norm"$-core"))
  .dependsOn(`$name;format="norm"$-db`)
  .enablePlugins(
    AkkaGrpcPlugin,
    $name;format="space,Camel"$CorePlugin
  )
  .settings(
    name := "$name;format="norm"$-core",
    Compile / appPackageName := "$package$.core",
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.coreDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.coreDependencyOverrides,
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufDependencies,
    libraryDependencies ++= $name;format="space,Camel"$Dependencies.protobufServiceDependencies,
    dependencyOverrides ++= $name;format="space,Camel"$Dependencies.protobufDependencyOverrides,
    g8ScaffoldTemplatesDirectory := baseDirectory.value / ".." / ".g8",
    akkaGrpcExtraGenerators ++= Seq(PlayScalaClientCodeGenerator),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Client)
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
    devSettings ++= Seq(
      "play.server.http.port" -> "$app_port$"
    ),
    Universal / packageName := name.value,
    topLevelDirectory := Some(packageName.value),
    Compile / fork := true,
    Compile / javaOptions ++= {
      val container = (Compile / appDevelopmentPostgresqlContainer).value
      Seq(
        s"-Dapp-server.app.database.postgresql.db.properties.url=\${container.jdbcUrl}&stringtype=unspecified",
        s"-Dapp-server.app.database.postgresql.db.properties.user=\${container.username}",
        s"-Dapp-server.app.database.postgresql.db.properties.password=\${container.password}"
      )
    },
    playRunHooks += {
      val classpath = (Compile / dependencyClasspath).value.files :+ (baseDirectory.value / "dummy-data")
      val container = (Compile / appDevelopmentPostgresqlContainer).value
      DevelopmentDatabaseHook(classpath, container)
    },
    playInteractionMode := AppInteractionMode.Default,
  )
