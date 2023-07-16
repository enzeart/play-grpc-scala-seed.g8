import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt._

case class SlickSourceCodeGeneratorParams(
    profile: String,
    jdbcDriver: String,
    outputDir: File,
    pkg: String,
    ignoreInvalidDefaults: Boolean,
    codeGeneratorClass: String,
    outputToMultipleFiles: Boolean
)

object SlickSourceGenerator {

  def apply(
      runner: ScalaRun,
      mainClass: String,
      classLoader: ClassLoader,
      classpath: Seq[File],
      params: SlickSourceCodeGeneratorParams,
      log: Logger,
      databaseContainer: PostgreSQLContainer
  ): Seq[File] = {
    FlywayMigration(classLoader, classpath, databaseContainer)

    runner
      .run(
        mainClass,
        classpath,
        Array(
          params.profile,
          params.jdbcDriver,
          databaseContainer.jdbcUrl,
          params.outputDir.getPath,
          params.pkg,
          databaseContainer.username,
          databaseContainer.password,
          params.ignoreInvalidDefaults.toString,
          params.codeGeneratorClass,
          params.outputToMultipleFiles.toString
        ),
        log
      )
      .failed
      .foreach(sys error _.getMessage)

    (params.outputDir / params.pkg.replace('.', '/')).listFiles().toSeq
  }
}
