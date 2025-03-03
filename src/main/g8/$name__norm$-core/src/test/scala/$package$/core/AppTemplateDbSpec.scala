package $package$.core

import $package$.core.util.ForAllApplicationDatabaseTestContainer
import $package$.db.Tables._
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

import java.time._
import java.util.UUID
import scala.util.Random

class AppTemplateDbSpec extends AsyncFreeSpec with ForAllApplicationDatabaseTestContainer with Matchers {

  "verify Slick and Slick-pg are working" in {
    import databaseConfig.profile.api._

    val row = AppTemplateDbTestRow(
      id = UUID.randomUUID(),
      textArray = List(Random.nextString(5)),
      int8Array = List(Random.nextLong()),
      int4Array = List(Random.nextInt()),
      int2Array = List(Random.nextInt().toShort),
      date = LocalDate.now(),
      time = LocalTime.now(),
      timetz = OffsetTime.now(),
      timestamptz = OffsetDateTime.now(),
      interval = Duration.ZERO
    )

    databaseConfig.db.run {
      for {
        _ <- AppTemplateDbTest.insertOrUpdate(row)
        insertedRow <- AppTemplateDbTest.filter(_.id === row.id).result.map(_.head)
      } yield insertedRow
    }.map(_ shouldEqual row)
  }
}
