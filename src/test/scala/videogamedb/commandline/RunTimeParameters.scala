package videogamedb.commandline

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RunTimeParameters extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  // ADD RUNTIME PARAMETERS IN GATLING
  def USERCOUNT = Integer.getInteger("USERS", 5)
  def RAMPDURATION = Integer.getInteger("RAMP_DURATION", 10)
  def TESTDURATION = Integer.getInteger("TEST_DURATION", 30)

  before {
    println(s"Running test with ${USERCOUNT} users")
    println(s"Ramping users over ${RAMPDURATION} seconds")
    println(s"Total test duration ${USERCOUNT} seconds")
  }

  def getAllVideoGames() = {
    exec(
      http(requestName = "Get all video games")
        .get("/videogame")
    ).pause(duration = 1)
  }

  val scn = scenario(name = "Run from command line")
    .forever {
      exec(getAllVideoGames())
    }

  setUp(
    scn.inject(
      nothingFor(5),
      rampUsers(USERCOUNT).during(RAMPDURATION)
    )
  ).protocols(httpProtocol)
    .maxDuration(TESTDURATION)

}
