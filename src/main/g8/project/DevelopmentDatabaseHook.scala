import com.dimafeng.testcontainers.PostgreSQLContainer
import org.flywaydb.core.Flyway
import play.sbt.PlayRunHook
import sbt.File

import scala.reflect.internal.util.ScalaClassLoader.URLClassLoader
import scala.util.Using
import sbt._

object DevelopmentDatabaseHook {

  def apply(classpath: Seq[File], container: PostgreSQLContainer): PlayRunHook = {
    new DevelopmentDatabaseHook(classpath, container)
  }
}

private class DevelopmentDatabaseHook (classpath: Seq[File], container: PostgreSQLContainer) extends PlayRunHook {

  override def beforeStarted(): Unit = {
    Using(new URLClassLoader(classpath.map(_.asURL), DevelopmentDatabaseHook.getClass.getClassLoader)) {
      classLoader => Flyway.configure(classLoader).dataSource(container.jdbcUrl, container.username, container.password).load().migrate()
    }
  }
}
