import giter8.ScaffoldPlugin
import sbt.{AutoPlugin, Def, _}

object $name;format="space,Camel"$ServerPlugin extends AutoPlugin {

  object autoImport {
  }

  import autoImport._

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq()

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
