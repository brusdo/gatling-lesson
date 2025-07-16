package videogamedb.commandline

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RunTimeParameters extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

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
      rampUsers(users = 10).during(10)
    )
  ).protocols(httpProtocol)
    .maxDuration(duration = 20)

}
