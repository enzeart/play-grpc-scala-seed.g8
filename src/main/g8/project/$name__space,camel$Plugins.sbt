// Utilities
addSbtPlugin("org.scalameta"            % "sbt-scalafmt"        % "$sbt_scalafmt_version$")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "$sbt_giter8_scaffold_version$")

addDependencyTreePlugin

// Play Framework
addSbtPlugin("org.playframework" % "sbt-plugin" % "$play_framework_version$")

// Protobuf
addSbtPlugin("org.apache.pekko" % "pekko-grpc-sbt-plugin" % "$pekko_grpc_sbt_plugin$")

libraryDependencies ++= Seq(
  "org.playframework"   %% "play-grpc-generators"     % "$play_grpc_generators_version$"
)

// Database
libraryDependencies ++= Seq(
  "org.flywaydb"       % "flyway-core"    % "$flyway_version$",
  "com.dimafeng" %% "testcontainers-scala-postgresql" % "$testcontainers_scala_version$",
  "org.postgresql"     % "postgresql"     % "$postgresql_version$"
)

// Git
libraryDependencies ++= Seq(
  "org.eclipse.jgit" % "org.eclipse.jgit" % "6.8.0.202311291450-r"
)

$if(codeartifact_support_enabled.truthy)$
// AWS CodeArtifact Support
addSbtPlugin("io.github.bbstilson" % "sbt-codeartifact" % "$sbt_codeartifact_version$")
$endif$

// Dependency Conflicts
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
