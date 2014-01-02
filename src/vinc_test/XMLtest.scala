package vinc_test

import java.io.IOException
import java.net.URL
import java.net.URLConnection
import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerException
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
//remove if not needed
import scala.collection.JavaConversions._

object NodeLister {

  def main(args: Array[String]) {
    val r = new read()
    val evntsList = new HashMap[String, String]()
    try {
      val url = new URL("http://a.goember.com/vast/db98791e231ad2c925fdbb39")
      val conn = url.openConnection()
      val factory = DocumentBuilderFactory.newInstance()
      val builder = factory.newDocumentBuilder()
      val doc = builder.parse(conn.getInputStream)
      val list = doc.getElementsByTagName("*")
      for (i <- 0 until list.getLength) {
        val element = list.item(i).asInstanceOf[Element]
        if ((element.getNodeName == "Impression")) {
          evntsList.put("Impression", r.pullLinks(element.getTextContent))
          
        } else if ((element.getNodeName == "Tracking")) {
          evntsList.put(element.getAttribute("event"), r.pullLinks(element.getTextContent))
        }
      }
      for ((key, value) <- evntsList) {
        println(key + " ----> " + value)
        println("")
      }
    } catch {
      case e: Exception => System.exit(1)
    }
  }
}
