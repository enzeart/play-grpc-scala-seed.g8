// Utilities
addSbtPlugin("org.scalameta"            % "sbt-scalafmt"        % "$sbt_scalafmt_version$")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "$sbt_giter8_scaffold_version$")

addDependencyTreePlugin

// Play Framework
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "$play_framework_version$")

// Protobuf
addSbtPlugin("com.lightbend.akka.grpc" % "sbt-akka-grpc" % "$sbt_akka_grpc_version$")

libraryDependencies ++= Seq(
  "com.lightbend.play"   %% "play-grpc-generators"     % "$play_grpc_generators_version$"
)

// Database
libraryDependencies ++= Seq(
  "org.flywaydb"       % "flyway-core"    % "$flyway_version$",
  "com.dimafeng" %% "testcontainers-scala-postgresql" % "$testcontainers_scala_version$",
  "org.postgresql"     % "postgresql"     % "$postgresql_version$"
)

$if(codeartifact_support_enabled.truthy)$
// AWS CodeArtifact Support
addSbtPlugin("io.github.bbstilson" % "sbt-codeartifact" % "$sbt_codeartifact_version$")
$endif$
