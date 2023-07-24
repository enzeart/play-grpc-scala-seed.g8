import $name;format="space,Camel"$Keys._
import giter8.ScaffoldPlugin
import giter8.ScaffoldPlugin.autoImport.g8Scaffold
import sbt.{AutoPlugin, Def, _}
import complete.DefaultParsers._

object $name;format="space,Camel"$CorePlugin extends AutoPlugin {

  val baseNameParser = Space ~> token(StringBasic).examples("<base_name>")

  val subPackageNameParser = Space ~> token(StringBasic).examples("<sub_package_name>")

  val appDbSpecScaffoldTask = Def.inputTaskDyn {
    val packageName = (Compile / appPackageName).value
    val (baseName, subPackageName) = (baseNameParser ~ subPackageNameParser).parsed
    g8Scaffold.toTask(s" dbspec --package=\$packageName --sub_package=\$subPackageName --base_name=\$baseName")
  }

  val baseProjectSettings: Seq[Def.Setting[_]] = Seq(
    appDbSpecScaffold := appDbSpecScaffoldTask.evaluated
  )

  override val trigger = noTrigger

  override val requires: Plugins = ScaffoldPlugin

  override lazy val projectSettings: Seq[Def.Setting[_]] = baseProjectSettings
}
