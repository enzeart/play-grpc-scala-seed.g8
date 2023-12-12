# $name$

## Environment Setup

### Docker

This project uses Docker to generate Slick code based on Flyway migrations
defined in the $name;format="norm"$-db subproject. Communication between the
build and the Docker daemon are managed by the Testcontainers library. Depending
on your particular Docker setup, you may have to tell Testcontainers
how to find your Docker daemon. The Testcontainers website provides
[instructions](https://www.testcontainers.org/features/configuration/)
on how to accomplish this.

#### macOS

For convenience, here's a script to quickly set up your development environment to build
this project when using Docker Desktop on Mac:

```bash
cat <<EOF > ~/.testcontainers.properties
docker.host=unix\:///var/run/docker.sock
EOF
```

$if(codeartifact_support_enabled.truthy)$
## AWS CodeArtifact Support

AWS CodeArtifact support is provided by the [sbt-codeartifact](https://github.com/bbstilson/sbt-codeartifact)
plugin. Take a look at the documentation for detailed information on how it works and
how it is configured.

When using this project with an IDE, you must ensure the build can find the region and credentials needed to
access your repository. The easiest option is to configure the default profiles in your ~/.aws/config
and ~/.aws/credentials files. If you'd prefer to take a different approach, consult the documentation for your IDE,
sbt-codeartifact, and AWS to see if there is a more suitable solution for your needs.

$endif$

## Frameworks and Tools

This project uses several frameworks and tools to implement its core features. Please see the below
documentation for more information.

### HTTP

The application server is [Play Framework](https://www.playframework.com/).

### Dependency Injection

Dependency injection is provided by [Guice](https://github.com/google/guice/wiki/GettingStarted) with scala extensions
provided by [ScalaGuice](https://github.com/codingwell/scala-guice).

### gPRC

gRPC supports is provided by [Play gRPC](https://github.com/playframework/play-grpc/blob/main/docs/src/main/paradox/play/index.md)
which is built on top of [Akka gRPC](https://doc.akka.io/docs/akka-grpc/2.1/overview.html).

### Database

Programmatic database access is provided by [Slick](https://scala-slick.org/).
Database schema management capabilities are provided by [Flyway](https://flywaydb.org/).

### Build Tool

[sbt](https://www.scala-sbt.org/) is the project's primary build tool.

## Multi-Project Development

The development server can be configured to execute commands in external projects that are included
as Git submodules. For example:

```scala
playRunHooks += GitSubmoduleServiceHook(
  repositoryRoot = baseDirectory.value.getParentFile,
  submoduleName = "modules/example-service",
  command = "sbt" :: "example-service-server/run" :: Nil
)
```

The above configuration will execute the command `sbt example-service-server/run` at the
file path registered in the `.gitmodules` configuration file for the submodule named
`modules/example-service` after the development server has been started.

You must manage everything else about the submodules yourself. Here are some useful commands
to help ensure that the project is up-to-date with all remote changes.

```bash
git submodule sync --recursive
git submodule update --init --recursive --remote
```


