package videogamedb.fundamentals

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Authenticate extends Simulation {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
    .contentTypeHeader(value = "application/json")

  def authenticate() = {
    exec(http(requestName = "Authenticate")
    .post(url= "/authenticate")
    .body(StringBody(string = "{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
    .check(jsonPath(path= "$.token").saveAs(key= "jwtToken")))
  }

  def createNewGame() = {
    exec(http(requestName = "Create new game")
      .post(url="/videogame")
      .header(name = "Authorization", value = "Bearer #{jwtToken}")
      .body(StringBody(
        // paste JSON in "double quotes" and will automatically format to a string
        string = "{\n  \"category\": \"Platform\",\n  \"name\": \"Mario\",\n  \"rating\": \"Mature\",\n  \"releaseDate\": \"2012-05-04\",\n  \"reviewScore\": 85\n}"
      )))
  }

  val scn = scenario(name = "Authenticate")
    .exec(authenticate())
    .exec(createNewGame())

  setUp(
    scn.inject(atOnceUsers(users = 1))
  ).protocols(httpProtocol)


}
