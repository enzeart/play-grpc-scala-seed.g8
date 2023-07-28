package $package$.core.domain.model

import org.scalatest.freespec.AnyFreeSpec

final class TestEntity(override val id: Int) extends Entity[Int](id) {

  override def canEqual(other: Any): Boolean = other.isInstanceOf[TestEntity]
}

class EntitySpec extends AnyFreeSpec {

  "An Entity" - {

    "should implement identity-based equivalence" in {
      val entity1 = new TestEntity(1)
      val entity2 = new TestEntity(1)
      assert(entity1 == entity2)
    }
  }
}
