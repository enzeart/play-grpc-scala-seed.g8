import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt.{File, settingKey, taskKey, inputKey}

object $name;format="space,Camel"$Keys {
  val appPackageName = settingKey[String]("The base package for the application project.")
  val appDevelopmentPostgresqlContainer = taskKey[PostgreSQLContainer]("PostgreSQL container for development purposes.")
  val appDbSpecScaffold = inputKey[Unit]("Generate a database test suite from the giter8 scaffold.")
  val appFlywayMigration = taskKey[Unit]("Apply the application's Flyway migration to a target database.")
}
