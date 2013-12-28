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
try {
  File file = new File("http://a.goember.com/vast/db98791e231ad2c925fdbb39");
       BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
       System.out.print("Enter XML File name: ");
       String xmlFile = bf.readLine();
       File file = new File(xmlFile);
       if(file.exists()){
         // Create a factory
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         // Use the factory to create a builder
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document doc = builder.parse(xmlFile);
         // Get a list of all elements in the document
         NodeList list = doc.getElementsByTagName("*");
         System.out.println("XML Elements: ");
         for (int i=0; i<list.getLength(); i++) {
           // Get element
           Element element = (Element)list.item(i);
           System.out.println(element.getNodeName());
         }
       }
       else{
         System.out.print("File not found!");
       }
     }
     catch (Exception e) {
       System.exit(1);
     }