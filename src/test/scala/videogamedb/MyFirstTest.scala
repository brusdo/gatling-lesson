package videogamedb

import io.gatling.core.Predef._
import io.gatling.http.Predef._


class MyFirstTest extends Simulation {

  // 1 Http Configuration: base URL, Headers
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json") // for every call will include it

  // 2 Scenario Definition: every step of script
  val scn = scenario( name = "My First Test")
    .exec(http("Get all games")
    .get("/videogame"))


  // 3 Load Scenario: Define loads scenario (number of users, how long run for)
  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

  // Run inside Engine Class



}
