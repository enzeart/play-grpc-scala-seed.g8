import com.dimafeng.testcontainers.PostgreSQLContainer
import play.sbt.PlayRunHook
import sbt.File

object DevelopmentDatabaseHook {

  def apply(classpath: Seq[File], databaseContainer: PostgreSQLContainer): PlayRunHook =
    new DevelopmentDatabaseHook(classpath, databaseContainer)
}

private class DevelopmentDatabaseHook (classpath: Seq[File], container: PostgreSQLContainer) extends PlayRunHook {

  override def beforeStarted(): Unit = FlywayMigration(DevelopmentDatabaseHook.getClass.getClassLoader, classpath, container)
}
