package vinc_test


import java.util.ArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern
//remove if not needed
import scala.collection.JavaConversions._

class read {

  def pullLinks(text: String): String = {
    var links = ""
    val regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]"
    val p = Pattern.compile(regex)
    val m = p.matcher(text)
    if (m.find()) {    
      var urlStr = m.group()
      if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
        urlStr = urlStr.substring(1, urlStr.length - 1)
        links = urlStr.toString
      }
    }
    println(links)
    links
  }
}
//  def pullLinks(text: String): String = {
//    val links = ""
//    val regex = "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]"
//    val p = Pattern.compile(regex)
//    val m = p.matcher(text)
//    for (urlStr <-m.group()) {
//      var url = urlStr.toString
//   
//      if (url.startsWith("(") && url.endsWith(")")) {
//        url = url.substring(1, url.length - 1)
//      }
//      links.concat(url)
//    }
//    links
//  }
