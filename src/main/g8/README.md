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
