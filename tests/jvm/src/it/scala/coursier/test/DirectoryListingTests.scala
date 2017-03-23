package coursier.test

import coursier._
import coursier.core.Authentication
import utest._

object DirectoryListingTests extends TestSuite {

  val user = "user"
  val password = "pass"

  val withListingRepo = MavenRepository(
    "http://localhost:8080",
    authentication = Some(Authentication(user, password))
  )

  val withoutListingRepo = MavenRepository(
    "http://localhost:8081",
    authentication = Some(Authentication(user, password))
  )

  val module = Module("com.abc", "test")
  val version = "0.1"

  val tests = TestSuite {
    'withListing - {
      'jar - CentralTests.withArtifacts(
        module,
        version,
        "jar",
        extraRepo = Some(withListingRepo)
      ) {
        artifacts =>
          assert(artifacts.length == 1)
      }

      'jarFoo - CentralTests.withArtifacts(
        module,
        version,
        "jar-foo",
        extraRepo = Some(withListingRepo)
      ) {
        artifacts =>
          assert(artifacts.length == 1)
      }
    }

    'withoutListing - {
      'jar - CentralTests.withArtifacts(
        module,
        version,
        "jar",
        extraRepo = Some(withoutListingRepo)
      ) {
        artifacts =>
          assert(artifacts.length == 1)
      }

      'jarFoo - CentralTests.withArtifacts(
        module,
        version,
        "jar-foo",
        extraRepo = Some(withoutListingRepo)
      ) {
        artifacts =>
          assert(artifacts.length == 0)
      }
    }
  }

}
