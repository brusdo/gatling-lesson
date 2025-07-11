package io.gatling.demo

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://api-ecomm.gatling.io")
    .inferHtmlResources(AllowList(), DenyList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*\.svg""", """.*detectportal\.firefox\.com.*""", """.*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*\.svg""", """.*detectportal\.firefox\.com.*"""))
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"Upgrade-Insecure-Requests" -> "1",
  		"sec-ch-ua" -> """Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "macOS"
  )
  
  private val headers_1 = Map(
  		"accept" -> "application/json, text/plain, */*",
  		"accept-encoding" -> "gzip, deflate, br, zstd",
  		"accept-language" -> "en-US,en;q=0.9",
  		"origin" -> "https://ecomm.gatling.io",
  		"priority" -> "u=1, i",
  		"sec-ch-ua" -> """Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "macOS",
  		"sec-fetch-dest" -> "empty",
  		"sec-fetch-mode" -> "cors",
  		"sec-fetch-site" -> "same-site"
  )
  
  private val headers_2 = Map(
  		"sec-ch-ua" -> """Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "macOS"
  )
  
  private val headers_9 = Map(
  		"accept" -> "*/*",
  		"accept-encoding" -> "gzip, deflate, br, zstd",
  		"accept-language" -> "en-US,en;q=0.9",
  		"access-control-request-headers" -> "authorization,content-type",
  		"access-control-request-method" -> "POST",
  		"origin" -> "https://ecomm.gatling.io",
  		"priority" -> "u=1, i",
  		"sec-fetch-dest" -> "empty",
  		"sec-fetch-mode" -> "cors",
  		"sec-fetch-site" -> "same-site"
  )
  
  private val headers_10 = Map(
  		"accept" -> "application/json, text/plain, */*",
  		"accept-encoding" -> "gzip, deflate, br, zstd",
  		"accept-language" -> "en-US,en;q=0.9",
  		"content-type" -> "application/json",
  		"origin" -> "https://ecomm.gatling.io",
  		"priority" -> "u=1, i",
  		"sec-ch-ua" -> """Not)A;Brand";v="8", "Chromium";v="138", "Google Chrome";v="138""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "macOS",
  		"sec-fetch-dest" -> "empty",
  		"sec-fetch-mode" -> "cors",
  		"sec-fetch-site" -> "same-site"
  )
  
  private val headers_11 = Map(
  		"accept" -> "*/*",
  		"accept-encoding" -> "gzip, deflate, br, zstd",
  		"accept-language" -> "en-US,en;q=0.9",
  		"access-control-request-headers" -> "authorization",
  		"access-control-request-method" -> "GET",
  		"origin" -> "https://ecomm.gatling.io",
  		"priority" -> "u=1, i",
  		"sec-fetch-dest" -> "empty",
  		"sec-fetch-mode" -> "cors",
  		"sec-fetch-site" -> "same-site"
  )
  
  private val uri2 = "https://ecomm.gatling.io"

  private val scn = scenario("RecordedSimulation")
    .exec(
      http("request_0")
        .get(uri2 + "/")
        .headers(headers_0)
        .resources(
          http("request_1")
            .get("/products?page=0&search=")
            .headers(headers_1),
          http("request_2")
            .get(uri2 + "/assets/images/sm-bag-pink.webp")
            .headers(headers_2),
          http("request_3")
            .get(uri2 + "/assets/images/bag-blue.webp")
            .headers(headers_2),
          http("request_4")
            .get(uri2 + "/assets/images/bottle-black.webp")
            .headers(headers_2),
          http("request_5")
            .get(uri2 + "/assets/images/tshirt-white.webp")
            .headers(headers_2),
          http("request_6")
            .get(uri2 + "/assets/images/tshirt-orange.webp")
            .headers(headers_2),
          http("request_7")
            .get(uri2 + "/assets/images/sm-bag-blue.webp")
            .headers(headers_2),
          http("request_8")
            .get(uri2 + "/assets/images/bag-yellow.webp")
            .headers(headers_2)
        ),
      pause(3),
      http("request_9")
        .options("/cart")
        .headers(headers_9)
        .resources(
          http("request_10")
            .post("/cart")
            .headers(headers_10)
            .body(RawFileBody("io/gatling/demo/recordedsimulation/0010_request.json"))
        ),
      pause(7),
      http("request_11")
        .options("/products/1")
        .headers(headers_11)
        .resources(
          http("request_12")
            .get("/products/1")
            .headers(headers_1)
        ),
      pause(1),
      http("request_13")
        .options("/cart")
        .headers(headers_9)
        .resources(
          http("request_14")
            .post("/cart")
            .headers(headers_10)
            .body(RawFileBody("io/gatling/demo/recordedsimulation/0014_request.json"))
        ),
      pause(1),
      http("request_15")
        .options("/products?page=0&search=")
        .headers(headers_11)
        .resources(
          http("request_16")
            .get("/products?page=0&search=")
            .headers(headers_1)
        ),
      pause(4),
      http("request_17")
        .options("/checkout")
        .headers(headers_9)
        .resources(
          http("request_18")
            .post("/checkout")
            .headers(headers_10)
            .body(RawFileBody("io/gatling/demo/recordedsimulation/0018_request.json"))
        )
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
