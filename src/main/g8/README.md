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
