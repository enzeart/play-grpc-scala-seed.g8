import giter8.ScaffoldPlugin
import sbt.{AutoPlugin, Def, _}

object $name;format="space,Camel"$Plugin extends AutoPlugin {

  object autoImport {
  }

  import autoImport._

  val base$name;format="space,Camel"$ProjectSettings: Seq[Def.Setting[_]] = Seq()

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = base$name;format="space,Camel"$ProjectSettings
}
