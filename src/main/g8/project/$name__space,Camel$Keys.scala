import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt.{File, settingKey, taskKey}

object $name;format="space,Camel"$Keys {
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
  val $name;format="space,camel"$DevelopmentPostgresqlContainer =
      taskKey[PostgreSQLContainer]("PostgreSQL container for development purposes")
}
