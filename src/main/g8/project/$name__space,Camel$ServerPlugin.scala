import com.dimafeng.testcontainers.PostgreSQLContainer
import giter8.ScaffoldPlugin
import sbt.{AutoPlugin, Def, _}

object $name;format="space,Camel"$ServerPlugin extends AutoPlugin {

  object autoImport {
    val $name;format="space,camel"$DevelopmentPostgresqlContainer =
      taskKey[PostgreSQLContainer]("PostgreSQL container for development purposes")
  }

  import autoImport._

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq.empty

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
