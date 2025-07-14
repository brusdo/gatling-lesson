package videogamedb.fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CheckResponseBodyAndExtract extends Simulation {

  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")

  val scn = scenario(name = "Check with JSON Path")

    .exec(http(requestName = "Get specific game")
    .get("/videogame/1")
    .check(jsonPath(path ="$.name").is(expected = "Resident Evil 4"))) // visit www.jsonpath.com and paste the jason expression $.name

    //Extract data from response for correlation
    .exec(http(requestName = "Get all video games")
    .get("/videogame")
    .check(jsonPath(path = "$[1].id").saveAs(key = "gameId")))
    .exec {session => println(session); session} // To debug, see what is inse '4gameId'

    .exec(http(requestName= "Get specific game")
    .get("/videogame/#{gameId}")
    .check(jsonPath(path ="$.name").is(expected = "Gran Turismo 3"))
    .check(bodyString.saveAs(key = "responseBody"))) // To debug, see the entire body response
    .exec { session => println(session("responseBody").as[String]); session} // print body4

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)

}
