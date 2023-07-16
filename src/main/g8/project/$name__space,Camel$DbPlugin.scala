import $name;format="space,Camel"$Keys._
import giter8.ScaffoldPlugin
import sbt.Keys._
import sbt._

object $name;format="space,Camel"$DbPlugin extends AutoPlugin {

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
    val databaseContainer         = (Compile / $name;format="space,camel"$DevelopmentPostgresqlContainer).value
    val classpath                 = (Compile / dependencyClasspath).value.files ++ additionalClasspath

    FlywayMigration(PlayGrpcTest4DbPlugin.getClass.getClassLoader, classpath, databaseContainer)

    runner.value
      .run(
        sourceGeneratorEntryPoint,
        classpath,
        Array(
          profile,
          jdbcDriver,
          databaseContainer.jdbcUrl,
          outputDir.getPath,
          pkg,
          databaseContainer.username,
          databaseContainer.password,
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
    $name;format="space,camel"$SlickCodegen := $name;format="space,camel"$SlickCodegenTask.value
  )

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
