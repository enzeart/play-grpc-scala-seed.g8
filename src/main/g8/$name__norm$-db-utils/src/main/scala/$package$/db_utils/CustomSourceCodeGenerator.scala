package $package$.db_utils

import slick.codegen.SourceCodeGenerator
import slick.model.Model
import slick.sql.SqlProfile.ColumnOption

class CustomSourceCodeGenerator(model: Model) extends SourceCodeGenerator(model) {

  override def Table = new Table(_) { table =>

    override def Column = new Column(_) { column =>

      override def rawType: String = {
        model.options
          .find(_.isInstanceOf[ColumnOption.SqlType])
          .flatMap { tpe =>
            tpe.asInstanceOf[ColumnOption.SqlType].typeName match {
              case "_text" | "text[]" | "_varchar" | "varchar[]" => Option("List[String]")
              case "_int8" | "int8[]"                            => Option("List[Long]")
              case "_int4" | "int4[]"                            => Option("List[Int]")
              case "_int2" | "int2[]"                            => Option("List[Short]")
              case _                                             => None
            }
          }
          .getOrElse {
            model.tpe match {
              case _ => super.rawType
            }
          }
      }

    }
  }

  override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String = {
    s"""
       |package $pkg
       |// AUTO-GENERATED Slick data model
       |/** Stand-alone Slick data model for immediate use */
       |object $container extends {
       |  val profile = $profile
       |} with $container
       |
       |/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
       |trait $container${parentType.map(t => s" extends $t").getOrElse("")} {
       |  val profile: $profile
       |  import profile.api._
       |  ${indent(code)}
       |}
       |""".stripMargin
  }

  protected def handleQuotedNamed(tableName: String): String = {
    if (tableName.endsWith("`")) s"${tableName.init}Table`" else s"${tableName}Table"
  }

  override def packageContainerCode(profile: String, pkg: String, container: String): String = {
    val mixinCode =
      codePerTable.keys.map(tableName => s"${handleQuotedNamed(tableName)}").mkString("extends ", " with ", "")

    s"""
       |package $pkg
       |// AUTO-GENERATED Slick data model
       |/** Stand-alone Slick data model for immediate use */
       |object $container extends {
       |  val profile = $profile
       |} with $container
       |
       |/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.)
       |    Each generated XXXXTable trait is mixed in this trait hence allowing access to all the TableQuery lazy vals.
       |  */
       |trait $container${parentType.map(t => s" extends $t").getOrElse("")} $mixinCode {
       |  val profile: $profile
       |  import profile.api._
       |  ${indent(codeForContainer)}
       |
       |}
       |""".stripMargin
  }
}
