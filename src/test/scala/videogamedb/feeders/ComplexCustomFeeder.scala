package videogamedb.feeders

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

class ComplexCustomFeeder extends Simulation {

  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json")
    .contentTypeHeader(value = "application/json") // Because we are making post call, on top to apply to all calls

  var idNumbers = (1 to 10).iterator
  val rnd = new Random()
  val now = LocalDate.now()
  var pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  def randomString(length: Int) = {
    rnd.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  def getRandomDate(startDate: LocalDate, random: Random) = {
    startDate.minusDays(random.nextInt(30)).format(pattern)
  }

  val customFeeder = Iterator.continually(Map(
    "gameId" -> idNumbers.next(),
    "name" -> ("Game" + randomString(length = 5)),
    "releaseDate" -> getRandomDate(now, rnd),
    "reviewScore" -> rnd.nextInt(100),
    "category" -> ("Category-" + randomString(length = 6)),
    "rating" -> ("Rating-" + randomString(length = 4))
  ))

  // For Post Call we always need to authenticate first
  def authenticate() = {
    exec(http(requestName = "Authenticate")
      .post(url = "/authenticate")
      .body(StringBody(string = "{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
      .check(jsonPath(path = "$.token").saveAs(key = "jwtToken")))
  }

    def createNewGame() = {
      repeat(times = 10) {
        feed(customFeeder)
          .exec(http(requestName = "Create new game - #{name}")
            .post(url = "/videogame")
            .header(name = "authorization", value = "Bearer #{jwtToken}")
            // to make it cleaner, added bodies new game json in resources
            .body(ElFileBody(filePath = "bodies/newGameTemplate.json")).asJson
            .check(bodyString.saveAs(key = "responseBody")))
          .exec { session => println(session("responseBody").as[String]); session }
          .pause(duration = 1)
      }
    }

    val scn = scenario(name = "Complex Custom Feeder")
      .exec(authenticate())
      .exec(createNewGame())

    setUp(
      scn.inject(atOnceUsers(users = 1))
    ).protocols(httpProtocol)
}
