package $package$.$sub_package$

import $package$.util.ForAllApplicationDatabaseTestContainer
import org.scalatest.freespec.FixtureAsyncFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{Checkpoints, FutureOutcome}

class $base_name;format="Camel"$Spec extends FixtureAsyncFreeSpec with ForAllApplicationDatabaseTestContainer with Matchers with Checkpoints {

  case class FixtureParam()

  override def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    import databaseConfig.profile.api._

    val fixture = FixtureParam()

    new FutureOutcome(for {
      outcome <- withFixture(test.toNoArgAsyncTest(fixture)).toFuture
      _ <- databaseConfig.db.run(DBIO.seq())
    } yield outcome)
  }
}
