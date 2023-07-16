import giter8.ScaffoldPlugin
import sbt._

object $name;format="space,Camel"$DbPlugin extends AutoPlugin {

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq.empty

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
