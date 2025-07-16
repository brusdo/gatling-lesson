package videogamedb.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicLoadSimulation extends Simulation {
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

  val scn = scenario(name = "Basic Load Simulation")
    .exec(getAllVideoGames())
    .pause(duration = 5)
    .exec(getSpecificGame())
    .pause(duration = 5)
    .exec(getAllVideoGames())

  // Different set up: not for single user anymore
  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(users = 5),
      rampUsers(users = 10).during(10)
    ).protocols(httpProtocol)
  )
}
