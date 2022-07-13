package modules

import com.google.inject.{AbstractModule, Provides}
import $package$.db_utils.CustomPostgresProfile
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration
import slick.basic.DatabaseConfig

import javax.inject.Singleton

class DatabaseModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = ()

  @Provides @Singleton
  def provideDatabaseConfig(configuration: Configuration): DatabaseConfig[CustomPostgresProfile] =
    DatabaseConfig.forConfig(path = "app.database.postgresql", config = configuration.underlying)
}
