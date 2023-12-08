import org.eclipse.jgit.api.Git
import org.eclipse.jgit.submodule.SubmoduleWalk
import play.sbt.PlayRunHook
import sbt.File

import scala.sys.process.Process
import scala.util.control.Breaks.{break, breakable}

object GitSubmoduleServiceHook {

  def apply(repositoryRoot: File, submoduleName: String, command: Seq[String]): PlayRunHook = {
    new GitSubmoduleServiceHook(repositoryRoot, submoduleName, command)
  }
}

private class GitSubmoduleServiceHook(repositoryRoot: File, submoduleName: String, command: Seq[String]) extends PlayRunHook {

  private val process: Option[Process] = None

  override def afterStarted(): Unit = {
    val submoduleWalk = SubmoduleWalk.forIndex(Git.open(repositoryRoot).getRepository)

    breakable {
      while (submoduleWalk.next()) {
        if (submoduleName == submoduleWalk.getModuleName) {
          Process(command, Option(submoduleWalk.getDirectory.getCanonicalFile)).run()
          break;
        }
      }
    }
  }

  override def afterStopped(): Unit = process.foreach(_.destroy())
}
