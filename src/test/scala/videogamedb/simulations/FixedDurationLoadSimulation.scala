package videogamedb.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class FixedDurationLoadSimulation extends Simulation {
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

  val scn = scenario(name = "Fixed Duration Load Simulation")
    .forever {
    exec(getAllVideoGames())
        .pause(duration = 5)
        .exec(getSpecificGame())
        .pause(duration = 5)
        .exec(getAllVideoGames())
    }

  // Different set up for Fixed Duration Load Simulation
  setUp(
    scn.inject(
      nothingFor(5),
      atOnceUsers(users = 10),
      rampUsers(users = 20).during(30)
    ).protocols(httpProtocol)
  ).maxDuration(duration = 60) //After this 60secs the test will stop
}