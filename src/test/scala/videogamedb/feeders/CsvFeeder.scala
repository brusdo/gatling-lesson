package videogamedb.feeders

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  //Different strategies: circular, random, etc
  val csvFeeder = csv(filePath = "data/gameCsvFile.csv").circular

  // Issue Version:
//  def getSpecificVideoGame() = {
//    repeat(times = 10) {
//      feed(csvFeeder)
//        .exec(http(requestName = "Get video game with name - #{gameName}")
//        .get("/videogame/#{gameId}")
//        .check(jsonPath(path = "$.name").is(expected = "#{gameName}"))
//        .check(status.is(expected = 200)))
//      pause(duration = 1)
//  }
//}

  //Solution:3
  def getSpecificVideoGame() =
    repeat(10)(
      feed(csvFeeder)
        .exec(
          http("Get video game with name - #{gameName}")
            .get("/videogame/#{gameId}")
            .check(jsonPath("$.name").is("#{gameName}"))
            .check(status.is(200))
        )
        .pause(1)
    )

  val scn = scenario(name = "Csv feeder test")
    .exec(getSpecificVideoGame())

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

}
