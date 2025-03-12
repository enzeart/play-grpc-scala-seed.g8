# $name$

## Environment Setup

### Docker

This project uses Docker to generate Slick code based on Flyway migrations
defined in the $name;format="norm"$-db subproject. Communication between the
build and the Docker daemon is managed by the Testcontainers library. Depending
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

Additionally, you must provide access to the Docker daemon inside any builds you run locally.
Docker Desktop makes this non-trivial, but it can be done with the following commands:

```bash
# This command starts a TCP socket server that will bind to the wildcard address
# and listen on port 2376. It will forward traffic to Docker Desktop's socket file
# on the host machine, effectively forwarding all traffic to Docker.
socat TCP-LISTEN:2375,reuseaddr,fork,bind=0.0.0.0 UNIX-CLIENT:/var/run/docker.sock

# Pass the DOCKER_HOST build argument to the build. The Dockerfile is already set up
# to use this value to configure the necessary environment variable. The address shown
# by 'ifconfig' for the en0 interface should work here.
docker build -t $name;format="norm"$-server:0.0.0 -f ./$name;format="norm"$-server/Dockerfile --build-arg DOCKER_HOST=tcp://\$(ipconfig getifaddr en0):2375 .
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

### gRPC

gRPC supports is provided by [Play gRPC](https://github.com/playframework/play-grpc/blob/main/docs/src/main/paradox/play/index.md)
which is built on top of [Pekko gRPC](https://pekko.apache.org/docs/pekko-grpc/current/).

### Database

Programmatic database access is provided by [Slick](https://scala-slick.org/).
Database schema management capabilities are provided by [Flyway](https://flywaydb.org/).

### Build Tool

[sbt](https://www.scala-sbt.org/) is the project's primary build tool.

## Multi-Project Development

The development server can be configured to execute commands in external projects that are included
as Git submodules. For example:

```scala
playInteractionMode := AppInteractionMode.Default,
playRunHooks ++= {
  implicit val sharedContext: SharedContext =
    SharedContext.forInteractionMode(
      interactionMode = playInteractionMode.value,
      logger = streams.value.log
    )

  Seq(
    GitSubmoduleServiceHook(
      repositoryRoot = baseDirectory.value.getParentFile,
      submoduleName = "modules/example-service",
      command = "sbt" :: "example-service-server/run" :: Nil
    )
  )
}
```

The above configuration will execute the command `sbt example-service-server/run` at the
file path registered in the `.gitmodules` configuration file for the submodule named
`modules/example-service` after the development server has been started.

You must manage everything else about the submodules yourself. Here are some useful commands
to help ensure that the project is up-to-date with all remote changes.

```bash
# There is a special situation that can happen when pulling superproject updates:
# it could be that the upstream repository has changed the URL of the submodule in the .gitmodules file
# in one of the commits you pull. This can happen for example if the submodule project changes its hosting platform.
# In that case, it is possible for git pull --recurse-submodules, or git submodule update, to fail if the superproject
# references a submodule commit that is not found in the submodule remote locally configured in your repository.
# In order to remedy this situation, the git submodule sync command is required.
git submodule sync --recursive

# Recursively initialize the working tree of all submodules that don't already exist. Also, fetch and update
# the contents of each submodule based on the state of the relevant remote tracking branch.
git submodule update --init --recursive --remote
```

### Excluding Duplicate Services

It is possible for a service to occur more than once in your application's service graph.
This can cause failures when starting the development servers due to port collisions, etc.
You can configure git to exclude the working tree of submodules using the
following commands.

```bash
# This will recursively initialize the working tree of all submodules included in the project.
# Most importantly, it will cause git to populate the .git/modules directory hierarchy where
# we can configure the submodules we want to exclude.
git submodule update --init --recursive

# This will clear out the working tree of all submodules while preserving all of the repository
# information and configurations stored in .git/modules.
git submodule deinit --all

# This will effectively configure the repository to prevent the submodule update command from populating the
# specified submodule's working tree.
git config -f .git/modules/<path_to_parent_of_submodule_to_exclude>/config submodule.<submodule_name>.update none
```

## Gitlab CI

### Docker-in-Docker
Gitlab CI can fail if the Docker-in-Docker service version is incompatible with the one installed in the build stage's
before_script section. If you run into any cryptic failures from testcontainer saying it cannot find a valid Docker environment,
make sure the major versions of these two docker installations are compatible.

## gRPC Server Reflection
Server Reflection is currently experimental and there is a lack of formal documentation around how to implement it with
Play gRPC. The following links provide enough context to achieve a working solution.

- [gRPC reflection in Play #11025](https://github.com/playframework/playframework/issues/11025)
- [haghard/play-scala-grpc-example](https://github.com/haghard/play-scala-grpc-example)
- [GreeterServiceReflectionRouter.scala](https://github.com/haghard/play-scala-grpc-example/blob/master/app/routers/GreeterServiceReflectionRouter.scala)
- [routes](https://github.com/haghard/play-scala-grpc-example/blob/master/conf/routes)
- [Server reflection is experimental #850](https://github.com/akka/akka-grpc/issues/850)

Server Reflection is implemented in the server subproject as `ServerReflectionRouter.scala`.

## Template Sanity Test and Cleanup
The project includes components used to sanity the test core functionality of what was generated from the template.
Running `sbt $name;format="norm"$-server/test` will execute these tests.
Here's a checklist of what to clean up after testing:

- [ ] Delete `V1__create_app_template_db_test_table.sql`
- [ ] Delete `AppTemplateServerSpec.scala`
- [ ] Remove the following from `$name;format="snake"$_service.proto`
  - The `Echo` rpc definition
  - The `EchoRequest` and `EchoReply` message definitions
- [ ] Remove the following from `$name;format="space,Camel"$ServiceRouter.scala`
  - The imports for `EchoRequest` and `EchoReply`
  - The `echo` method
- [ ] Remove the following from `HttpApiController.scala`
  - The import for `EchoRequest`
  - The `echo` method
- [ ] Remove the following from `routes`
  - The route for `/echo`
- [ ] Remove this section from the documentation


