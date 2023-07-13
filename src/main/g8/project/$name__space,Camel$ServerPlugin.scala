import com.dimafeng.testcontainers.PostgreSQLContainer
import giter8.ScaffoldPlugin
import sbt.{AutoPlugin, Def, _}
import org.flywaydb.core.Flyway
import org.testcontainers.utility.DockerImageName
import sbt.Keys.dependencyClasspath

import scala.reflect.internal.util.ScalaClassLoader.URLClassLoader
import scala.sys.process.Process
import scala.util.Using

object $name;format="space,Camel"$ServerPlugin extends AutoPlugin {

  object autoImport {
    val $name;format="space,camel"$AppDatabaseDockerImage =
      settingKey[String]("The database Docker image to use while running the application in development mode")
    val $name;format="space,camel"$AppFlywayAdditionalClasspath =
      settingKey[Seq[File]]("Additional classpath entries for configuring Flyway")
    val $name;format="space,camel"$AppStart = inputKey[Unit]("Start the application in development mode")
  }

  import autoImport._

  val $name;format="space,camel"$AppStartTask = Def.taskDyn {
    val databaseDockerImage = (Compile / $name;format="space,camel"$AppDatabaseDockerImage).value
    val additionalClasspath = (Compile / $name;format="space,camel"$AppFlywayAdditionalClasspath).value
    val classpath = (Compile / dependencyClasspath).value.files ++ additionalClasspath
    val databaseContainer = PostgreSQLContainer(DockerImageName.parse(databaseDockerImage))

    databaseContainer.start()

    val jdbcUrl = databaseContainer.jdbcUrl
    val username = databaseContainer.username
    val password = databaseContainer.password
    val environmentVariables = Seq(
      "APP_DB_URL" -> s"\$jdbcUrl&stringtype=unspecified",
      "APP_DB_USER" -> username,
      "APP_DB_PASSWORD" -> password
    )
    val sbtRun = Process("sbt" :: "$name;format="norm"$-server/run" :: Nil, cwd = None, extraEnv = environmentVariables: _*)

    Using(new URLClassLoader(classpath.map(_.asURL), $name;format="space,Camel"$ServerPlugin.getClass.getClassLoader)) {
      classLoader => Flyway.configure(classLoader).dataSource(jdbcUrl, username, password).load().migrate()
    }

    Def.task(sbtRun.!)
  }

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq(
    $name;format="space,camel"$AppDatabaseDockerImage := "postgres:latest",
    $name;format="space,camel"$AppFlywayAdditionalClasspath := Seq.empty,
    $name;format="space,camel"$AppStart := $name;format="space,camel"$AppStartTask.value
  )

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
