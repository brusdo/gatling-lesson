package videogamedb.fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExtract extends Simulation {

  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  val scn = scenario(name = "Check with JSON Path")

    .exec(http(requestName = "Get specific game")
    .get("/videogame/1")
    .check(jsonPath("$.name").is(expected = "Resident Evil 4"))) // visit www.jsonpath.com and paste the jason expression $.name

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

}
