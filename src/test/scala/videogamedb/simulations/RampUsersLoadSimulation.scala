package videogamedb.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RampUsersLoadSimulation extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  def getAllVideoGames() = {
    exec(
      http(requestName = "Get all video games")
        .get("/videogame")
    )
  }

  def getSpecificGame() = {
    exec(
      http(requestName = "Get specific video games")
        .get("/videogame/2")
    )
  }

  val scn = scenario(name = "Ramp Users Load Simulation")
    .exec(getAllVideoGames())
    .pause(duration = 5)
    .exec(getSpecificGame())
    .pause(duration = 5)
    .exec(getAllVideoGames())

  // Different set up for RAMP USERS LOAD SIMULATION
  setUp(
    scn.inject(
      nothingFor(5),
      constantUsersPerSec(rate = 10).during(10),
      rampUsersPerSec(rate1 = 1).to(rate2 = 5).during(20)
    ).protocols(httpProtocol)
  )
}
