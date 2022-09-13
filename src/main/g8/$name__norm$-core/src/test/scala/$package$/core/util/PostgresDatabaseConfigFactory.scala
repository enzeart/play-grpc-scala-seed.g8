package $package$.core.util

import com.dimafeng.testcontainers.PostgreSQLContainer
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import $package$.db_utils.CustomPostgresProfile
import slick.basic.DatabaseConfig

object PostgresDatabaseConfigFactory {

  val Default: PostgresDatabaseConfigFactory =
    new PostgresDatabaseConfigFactory("app.database.postgresql", ConfigFactory.defaultApplication())
}

class PostgresDatabaseConfigFactory(path: String, config: Config) {

  def forContainer(container: PostgreSQLContainer): DatabaseConfig[CustomPostgresProfile] = {
    DatabaseConfig.forConfig(
      path,
      config
        .withValue(
          s"\$path.db.properties.url",
          ConfigValueFactory.fromAnyRef(s"\${container.jdbcUrl}&stringtype=unspecified")
        )
        .withValue(s"\$path.db.properties.user", ConfigValueFactory.fromAnyRef(container.username))
        .withValue(s"\$path.db.properties.password", ConfigValueFactory.fromAnyRef(container.password))
    )
  }
}
