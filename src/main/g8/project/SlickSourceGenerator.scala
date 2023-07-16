import com.dimafeng.testcontainers.PostgreSQLContainer
import sbt._

object SlickSourceGenerator {

  def apply(
      runner: ScalaRun,
      mainClass: String,
      classLoader: ClassLoader,
      classpath: Seq[File],
      profile: String,
      jdbcDriver: String,
      outputDir: File,
      pkg: String,
      ignoreInvalidDefaults: Boolean,
      codeGeneratorClass: String,
      outputToMultipleFiles: Boolean,
      log: Logger,
      databaseContainer: PostgreSQLContainer
  ): Seq[File] = {
    FlywayMigration(classLoader, classpath, databaseContainer)

    runner
      .run(
        mainClass,
        classpath,
        Array(
          profile,
          jdbcDriver,
          databaseContainer.jdbcUrl,
          outputDir.getPath,
          pkg,
          databaseContainer.username,
          databaseContainer.password,
          ignoreInvalidDefaults.toString,
          codeGeneratorClass,
          outputToMultipleFiles.toString
        ),
        log
      )
      .failed
      .foreach(sys error _.getMessage)

    (outputDir / pkg.replace('.', '/')).listFiles().toSeq
  }
}
