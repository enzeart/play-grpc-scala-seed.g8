package $package$.core.util

import com.dimafeng.testcontainers.{ForAllTestContainer, PostgreSQLContainer}
import $package$.db_utils.CustomPostgresProfile
import org.flywaydb.core.Flyway
import org.scalatest.Suite
import org.testcontainers.utility.DockerImageName
import slick.basic.DatabaseConfig

trait ForAllApplicationDatabaseTestContainer extends ForAllTestContainer { self: Suite =>

  val imageVersion: String = "latest"

  override lazy val container: PostgreSQLContainer = PostgreSQLContainer(
    DockerImageName.parse(s"postgres:\$imageVersion")
  )

  lazy val databaseConfig: DatabaseConfig[CustomPostgresProfile] =
    PostgresDatabaseConfigFactory.Default.forContainer(container)

  override def afterStart(): Unit = {
    super.afterStart()
    Flyway.configure().dataSource(container.jdbcUrl, container.username, container.password).load().migrate()
  }
}
