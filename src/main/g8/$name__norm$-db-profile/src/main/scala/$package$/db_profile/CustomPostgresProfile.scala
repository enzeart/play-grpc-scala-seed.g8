package $package$.db_profile
import com.github.tminglei.slickpg._
import slick.jdbc.{GetResult, PositionedResult}

import java.time.{Duration, LocalDate, LocalDateTime, LocalTime, OffsetDateTime, OffsetTime}
import java.util.UUID
import scala.reflect.classTag

trait CustomPostgresProfile
    extends ExPostgresProfile
    with PgArraySupport
    with PgDate2Support
    with PgRangeSupport
    with PgHStoreSupport
    with PgSearchSupport
    with PgNetSupport
    with PgLTreeSupport {

  // Add back `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  override protected def computeCapabilities: Set[slick.basic.Capability] =
    super.computeCapabilities + slick.jdbc.JdbcCapabilities.insertOrUpdate

  override val api: API = CustomAPI

  object CustomAPI
      extends super.API
      with ArrayImplicits
      with DateTimeImplicits
      with NetImplicits
      with LTreeImplicits
      with RangeImplicits
      with HStoreImplicits
      with SearchImplicits
      with SearchAssistants {

    implicit object GetUuid extends GetResult[UUID] {
      override def apply(p: PositionedResult): UUID = UUID.fromString(p.nextString())
    }
  }

}

object CustomPostgresProfile extends CustomPostgresProfile {

  bindPgDateTypesToScala(
    classTag[LocalDate],
    classTag[LocalTime],
    classTag[LocalDateTime],
    classTag[OffsetTime],
    classTag[OffsetDateTime],
    classTag[Duration]
  )
}
