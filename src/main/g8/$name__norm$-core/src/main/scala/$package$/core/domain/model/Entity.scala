package $package$.core.domain.model

abstract class Entity[A](val id: A) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[Entity[_]]

  override def equals(other: Any): Boolean = other match {
    case that: Entity[_] => that.canEqual(this) && id == that.id
    case _               => false
  }

  override def hashCode(): Int = {
    val state = Seq(id)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
