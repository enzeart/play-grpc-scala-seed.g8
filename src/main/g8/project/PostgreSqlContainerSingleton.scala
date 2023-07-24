import com.dimafeng.testcontainers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

object PostgreSqlContainerSingleton {

  lazy val Instance: PostgreSQLContainer = {
    val container = PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
    container.start()
    container
  }

}
