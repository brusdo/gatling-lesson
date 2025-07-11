package videogamedb.fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class CheckResponseCode extends Simulation {
  // 1 Http Configuration: base URL, Headers
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json") // for every call will include it

  // 2 Scenario Definition: every step of script
  val scn = scenario(name = "Video Game DB - 3 calls")

    // add 1st http transaction
    .exec(http(requestName = "Get all video games - 1st call")
      .get("/videogame")
      .check(status.is(expected = 200))) // Default is always 200, just making it explicity
    .pause(duration = 5) // 5 seconds

    // add 2st http transaction
    .exec(http(requestName = "Get specific game")
      .get("/videogame/1")
      .check(status.in(expected = 200 to 210))) // Check if the response is in the range
    .pause(1,10)

    // add 3st http transaction
    .exec(http(requestName = "Get all video games - 2nd call")
      .get("/videogame/1")
    .check(status.not(expected = 404), status.not(expected = 500))) // Check if the response is not 404 and 500
    .pause(3000.milliseconds)

  // 3 Load Scenario: Define loads scenario (number of users, how long run for)
  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

}