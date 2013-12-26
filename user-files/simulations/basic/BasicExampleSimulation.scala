package basic

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import io.gatling.http.Headers.Names._
import scala.concurrent.duration._
import bootstrap._
import assertions._
import com.ning.http.client._
import java.util.concurrent.Future
import scala.xml._

class BasicExampleSimulation extends Simulation {

	val client = new AsyncHttpClient()
  val response = client.prepareGet("test").execute().get()
  val ad_tag = XML.loadString(response.getResponseBody().toString)

  // regular expression method
  // val impression = "<Impression>\\n\\S+\\nhttp:\\/\\/.+\\n\\S+<\\/Impression>".r
  // val impress = scala.impression findAllIn ad_tag
  // println(impress)

  // parsing xml method
  // val impression = ad_tag \\ "Impression"
  // val impression_pattern = "http:\\/\\/.+".r
  // val impression_url = impression_pattern findAllIn impression.toString
  // println(impression_url.mkString(""))

  val ad_object = new ParseXMLRegex()
  val ad_string = ad_object.parse_object(ad_tag)
  println(ad_string)

	val httpProtocol = http
		.baseURL("test")
		.acceptCharsetHeader("ISO-8859-1,utf-8;q=0.7,*;q=0.7")
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("fr,fr-fr;q=0.8,en-us;q=0.5,en;q=0.3")
		.disableFollowRedirect


	val headers_1 = Map(
		"Keep-Alive" -> "115")

	val headers_3 = Map(
		"Keep-Alive" -> "115",
		"Content-Type" -> "application/x-www-form-urlencoded")

	val headers_6 = Map(
		"Accept" -> "application/json, text/javascript, */*; q=0.01",
		"Keep-Alive" -> "115",
		"X-Requested-With" -> "XMLHttpRequest")

	val scn = scenario("Scenario name")
		.group("Ads") {
			exec(
				http("get ad unit")
					.get("/db98791e231ad2c925fdbb39")
					.headers(headers_1)
					.check(status.is(200)))
				.pause(0 milliseconds, 100 milliseconds)
		}

	setUp(scn.inject(ramp(100 users) over (5 seconds)))
		.protocols(httpProtocol)
		.assertions(
			global.successfulRequests.percent.is(100), details("Login" / "request_2").responseTime.max.lessThan(2000),
			details("request_9").requestsPerSec.greaterThan(10))
}
