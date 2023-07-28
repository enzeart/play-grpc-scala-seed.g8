package $package$.core.domain.model

trait Repository[A, B <: Entity[A], C[_]] {

  def findById(id: A): C[Option[B]]

  def save(entity: B): C[Unit]

  def remove(entity: B): C[Unit]
}
