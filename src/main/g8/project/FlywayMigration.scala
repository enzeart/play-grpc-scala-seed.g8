import com.dimafeng.testcontainers.PostgreSQLContainer
import org.flywaydb.core.Flyway
import sbt._

import scala.reflect.internal.util.ScalaClassLoader.URLClassLoader
import scala.util.Using

object FlywayMigration {

  def apply(classloader: ClassLoader, classpath: Seq[File], databaseContainer: PostgreSQLContainer): Unit = {
    Using(new URLClassLoader(classpath.map(_.asURL), classloader)) { classLoader =>
      val jdbcUrl = databaseContainer.jdbcUrl
      val username = databaseContainer.username
      val password = databaseContainer.password
      Flyway.configure(classLoader).dataSource(jdbcUrl, username, password).load().migrate()
    }
  }
}
