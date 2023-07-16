import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt.{File, settingKey, taskKey}

object $name;format="space,Camel"$Keys {
  val $name;format="space,camel"$DevelopmentPostgresqlContainer =
      taskKey[PostgreSQLContainer]("PostgreSQL container for development purposes")
}
