FROM ubuntu:latest AS builder

WORKDIR /app
COPY . .

RUN apt-get update && apt-get install -y curl zip openjdk-11-jre
SHELL ["/bin/bash", "-c"]
RUN curl -s https://get.sdkman.io | bash
RUN source "/root/.sdkman/bin/sdkman-init.sh" && sdk install sbt

CMD ["/bin/bash", "-c", "source /root/.sdkman/bin/sdkman-init.sh && sbt $name;format="norm"$-db/appFlywayMigration"]
