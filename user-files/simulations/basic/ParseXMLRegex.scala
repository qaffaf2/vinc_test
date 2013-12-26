package basic

import scala.util.matching.Regex
import scala.xml._

class ParseXMLRegex {
  // var ad_tag = new scala.xml.Elem(null, "", scala.xml.Null , scala.xml.TopScope)

  def parse_object(ad_tag: scala.xml.Elem) : String = {
    val impression = get_ad_link("Impression", ad_tag)
    val creative = get_ad_link("Creatives", ad_tag, "creativeView")
    return creative
  }

  def get_ad_link(input: String, ad_tag: scala.xml.Elem, eventInput: String = "") : String = {
    var xml = ""
    if (input == "Impression")
      xml = (ad_tag \\ input).toString
    else{
      xml = (ad_tag \\ input \\ "Creative" \\ "Linear" \\ "TrackingEvents" \\ "Tracking" \\ s"@event='$eventInput'").toString
      println(xml)// xml = ad_tag \\ input \\ "Creative" \\ "Linear" \\ "TrackingEvents" \\ "Tracking" \ "@event" if (event text).equals(eventInput)
    }

    val pattern = "http:\\/\\/.+".r
    val url = pattern findAllIn xml
    return url.mkString("")
  }

  def attributeValueEquals(value: String)(node: Node) = {
    node.attributes.exists(_.value.text == value)
  }

}