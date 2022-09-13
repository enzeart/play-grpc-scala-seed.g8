import com.dimafeng.testcontainers.PostgreSQLContainer
import giter8.ScaffoldPlugin
import org.flywaydb.core.Flyway
import org.testcontainers.utility.DockerImageName
import sbt.Keys._
import sbt._

import java.util.UUID
import scala.reflect.internal.util.ScalaClassLoader.URLClassLoader
import scala.util.Using

object $name;format="space,Camel"$DbPlugin extends AutoPlugin {

  object autoImport {
    val $name;format="space,camel"$SlickCodegenAdditionalClasspath =
      settingKey[Seq[File]]("Additional classpath entries for Slick code generation")
    val $name;format="space,camel"$SlickCodegenJdbcDriver =
      settingKey[String]("The FQCN of the JDBC driver used for Slick code generation")
    val $name;format="space,camel"$SlickCodegenProfile = settingKey[String]("The FQCN of the profile used for Slick code generation")
    val $name;format="space,camel"$SlickCodegenPackage = settingKey[String]("The package used for generated Slick code")
    val $name;format="space,camel"$SlickCodegenOutputDir =
      settingKey[File]("The output directory where generated Slick code is stored")
    val $name;format="space,camel"$SlickCodegenSourceGeneratorEntryPoint =
      settingKey[String]("The FQCN of the Slick source code generator entrypoint")
    val $name;format="space,camel"$SlickCodegenSourceGeneratorClass =
      settingKey[String]("The FQCN of the Slick source code generator class")
    val $name;format="space,camel"$SlickCodegenOutputToMultipleFiles =
      settingKey[Boolean]("Output generated Slick code to multiple files?")
    val $name;format="space,camel"$SlickCodegenIgnoreInvalidDefaults =
      settingKey[Boolean]("Ignore invalid default values for generated Slick code?")
    val $name;format="space,camel"$SlickCodegenDatabaseDockerImage =
      settingKey[String]("The database Docker image to use for Slick code generation")
    val $name;format="space,camel"$SlickCodegen = taskKey[Seq[File]]("Generate Slick code based on Flyway migrations")
  }

  import autoImport._

  val $name;format="space,camel"$SlickCodegenTask = Def.taskDyn {
    val additionalClasspath       = (Compile / $name;format="space,camel"$SlickCodegenAdditionalClasspath).value
    val jdbcDriver                = (Compile / $name;format="space,camel"$SlickCodegenJdbcDriver).value
    val profile                   = (Compile / $name;format="space,camel"$SlickCodegenProfile).value
    val pkg                       = (Compile / $name;format="space,camel"$SlickCodegenPackage).value
    val outputDir                 = (Compile / $name;format="space,camel"$SlickCodegenOutputDir).value
    val sourceGeneratorEntryPoint = (Compile / $name;format="space,camel"$SlickCodegenSourceGeneratorEntryPoint).value
    val sourceGeneratorClass      = (Compile / $name;format="space,camel"$SlickCodegenSourceGeneratorClass).value
    val outputToMultipleFiles     = (Compile / $name;format="space,camel"$SlickCodegenOutputToMultipleFiles).value.toString
    val ignoreInvalidDefaults     = (Compile / $name;format="space,camel"$SlickCodegenIgnoreInvalidDefaults).value.toString
    val databaseDockerImage       = (Compile / $name;format="space,camel"$SlickCodegenDatabaseDockerImage).value
    val classpath                 = (Compile / dependencyClasspath).value.files ++ additionalClasspath

    Using.resources(
      PostgreSQLContainer(DockerImageName.parse(databaseDockerImage)),
      new URLClassLoader(classpath.map(_.asURL), $name;format="space,Camel"$DbPlugin.getClass.getClassLoader)
    ) { (databaseContainer, classLoader) =>
      databaseContainer.start()

      val jdbcUrl  = databaseContainer.jdbcUrl
      val username = databaseContainer.username
      val password = databaseContainer.password

      Flyway.configure(classLoader).dataSource(jdbcUrl, username, password).load().migrate()

      runner.value
        .run(
          sourceGeneratorEntryPoint,
          classpath,
          Array(
            profile,
            jdbcDriver,
            jdbcUrl,
            outputDir.getPath,
            pkg,
            username,
            password,
            ignoreInvalidDefaults,
            sourceGeneratorClass,
            outputToMultipleFiles
          ),
          streams.value.log
        )
        .failed
        .foreach(sys error _.getMessage)

      Def.task {
        (outputDir / pkg.replace('.', '/')).listFiles().toSeq
      }
    }
  }

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq(
    $name;format="space,camel"$SlickCodegenAdditionalClasspath := Seq.empty,
    $name;format="space,camel"$SlickCodegenJdbcDriver := "org.postgresql.Driver",
    $name;format="space,camel"$SlickCodegenProfile := "$package$.db_utils.CustomPostgresProfile",
    $name;format="space,camel"$SlickCodegenPackage := "$package$.db",
    $name;format="space,camel"$SlickCodegenOutputDir := (Compile / sourceManaged).value,
    $name;format="space,camel"$SlickCodegenSourceGeneratorEntryPoint := "slick.codegen.SourceCodeGenerator",
    $name;format="space,camel"$SlickCodegenSourceGeneratorClass := "$package$.db_utils.CustomSourceCodeGenerator",
    $name;format="space,camel"$SlickCodegenOutputToMultipleFiles := true,
    $name;format="space,camel"$SlickCodegenIgnoreInvalidDefaults := true,
    $name;format="space,camel"$SlickCodegenDatabaseDockerImage := "postgres:latest",
    $name;format="space,camel"$SlickCodegen := $name;format="space,camel"$SlickCodegenTask.value
  )

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
