package videogamedb.finalsimulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class VideoGameFullTest extends Simulation  {
  val httpProtocol = http.baseUrl(url = "https://videogamedb.uk/api")
    .acceptHeader(value = "application/json") // ideally add to POST calls, but no impact to add to all calls by default

  // ADD VARIABLE FOR FEEDERS
  //Runtime variables
  def USERCOUNT = Integer.getInteger("USERS", 5)
  def RAMPDURATION = Integer.getInteger("RAMP_DURATION", 10)
  def TESTDURATION = Integer.getInteger("TEST_DURATION", 30)

  // ADD FEEDER (Not using custom feeder)
  val csvFeeder = csv(filePath = "data/gameCsvFile.csv").random

  before {
    println(s"Running test with ${USERCOUNT} users")
    println(s"Ramping users over ${RAMPDURATION} seconds")
    println(s"Total test duration ${TESTDURATION} seconds")
  }

  // *** ADD HTTP CALLS ***
  // GET ALL GAMES
  def getAllVideoGames() = {
    exec(
      http(requestName = "Get all video games")
        .get("/videogame")
        .check(status.is(200))
    )
  }

  // AUTHENTICATE CALLS
  def authenticate() = {
    exec(http(requestName = "authenticate")
      .post(url= "/authenticate")
      .body(StringBody(string = "{\n  \"password\": \"admin\",\n  \"username\": \"admin\"\n}"))
      .check(jsonPath(path= "$.token").saveAs(key= "jwtToken")))
  }

  // CREATE NEW GAME CALL (WITH CSV FEEDER)
  def createNewGame() = {
    feed(csvFeeder)
    .exec(http(requestName = "Create new game - #{name}")
      .post(url="/videogame")
      .header(name = "authorization", value = "Bearer #{jwtToken}")
      .body(ElFileBody(filePath = "bodies/newGameTemplate.json")).asJson
//      .check(status.is(200))
//      .check(jsonPath("$.id").saveAs("gameId")) //Save game ID
    )
  }

    // GET DETAILS OF SINGLE GAME
    def getSingleGame() = {
    exec(http(requestName = "Create new game - #{name}")
    .get(url="/videogame/#{gameId}")
    .check(jsonPath(path = "$.name").is(expected = "#{name}"))) //Check if name matches
    }


    // DELETE GAME
    def deleteGame() =  {
      exec(http(requestName = "Delete game - #{name}")
      .delete(url ="/videogame/#{gameId}")
      .header(name = "Authorization", value = "Bearer #{jwtToken}")
      .check(bodyString.is(expected = "Video game deleted")))
  }

  // SCENARIO DESIGN
  val scn = scenario("Video Game DB Final Script")
  .forever {
    exec(getAllVideoGames())
      .pause(2)
      .exec(authenticate())
      .pause(2)
      .exec(createNewGame())
      .pause(2)
      .exec(getSingleGame())
      .pause(2)
      .exec(deleteGame())
  }

  // TO DO: ADD SETUP LOAD SIMULATION
  // CREATE A SIMULATION THAT HAS RUNTIME PARAMETERS:
  // 1. USERS
  // 2. RAMP UP TIME
  // 3. TEST DURATION
  setUp(
    scn.inject(
      nothingFor(5),
      rampUsers(USERCOUNT).during(RAMPDURATION)
    )
  ).protocols(httpProtocol)
    .maxDuration(TESTDURATION)

  // AFTER BLOCKS
  after {
    println("Stress test completed")
  }

}
