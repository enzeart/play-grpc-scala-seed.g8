package $package$.db_utils

import slick.codegen.SourceCodeGenerator
import slick.model.Model
import slick.sql.SqlProfile.ColumnOption

class CustomSourceCodeGenerator(model: Model) extends SourceCodeGenerator(model) {

  override def Table = new Table(_) { table =>

    override def Column = new Column(_) { column =>

      override def rawType: String = {
        this.model.options
          .find(_.isInstanceOf[ColumnOption.SqlType])
          .flatMap { tpe =>
            tpe.asInstanceOf[ColumnOption.SqlType].typeName match {
              case "_text" | "text[]" | "_varchar" | "varchar[]" => Option("List[String]")
              case "_int8" | "int8[]" => Option("List[Long]")
              case "_int4" | "int4[]" => Option("List[Int]")
              case "_int2" | "int2[]" => Option("List[Short]")
              case _ => None
            }
          }
          .getOrElse {
            this.model.tpe match {
              case _ => super.rawType
            }
          }
      }
    }
  }

  override def packageCode(profile: String, pkg: String, container: String, parentType: Option[String]): String =
    super.packageCode(profile, pkg, container, parentType).replace("slick.jdbc.JdbcProfile", profile)

  override def packageContainerCode(profile: String, pkg: String, container: String): String =
    super.packageContainerCode(profile, pkg, container).replace("slick.jdbc.JdbcProfile", profile)

  override def rootTraitCode(profile: String, pkg: String, container: String): String =
    super.rootTraitCode(profile, pkg, container).replace("slick.jdbc.JdbcProfile", profile)
}
