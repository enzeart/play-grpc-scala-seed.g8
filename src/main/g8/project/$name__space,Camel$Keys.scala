import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt.{File, settingKey, taskKey}

object $name;format="space,Camel"$Keys {
  val appDevelopmentPostgresqlContainer =
    taskKey[PostgreSQLContainer]("PostgreSQL container for development purposes")
}
