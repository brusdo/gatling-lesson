package videogamedb.fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CodeReuse extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  // Create def methods:
  def getAllVideoGames() = {
    repeat(times = 3) { // TO REPEAT
      exec(http(requestName = "Get all video games")
        .get("/videogame")
        .check(status.is(expected = 200)))
    }
  }

  def getSpecificGame() = {
    repeat(times = 5, counterName = "counter") { //counter will increment by one each time
      exec(http(requestName = "Get specific game with ID: #{counter}")
        .get("/videogame/#{counter}") //Will return an error - expected
        .check(status.in(expected = 200 to 210)))
    }
  }

  val scn = scenario(name = "Code reuse")
//  Add methods into the scenario block:
    .exec(getAllVideoGames())
    .pause(duration = 5)
    .exec(getSpecificGame())
    .pause(duration = 5)
    .repeat(times =2) {
      getAllVideoGames()
    }

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

}
