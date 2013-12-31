package vinc_test

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
class XMLtest {
  
  public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, TransformerException {

		read r = new read();
		Map<String, ArrayList> evntsList = new HashMap<String, ArrayList>();
		// ArrayList because in VMAP there could be more than one link in the Impression
	
		    
		try {
			URL url = new URL("http://a.goember.com/vast/db98791e231ad2c925fdbb39");
	        URLConnection conn = url.openConnection();
		
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(conn.getInputStream());
				// Get a list of all elements in the document
				NodeList list = doc.getElementsByTagName("*");
				for (int i = 0; i < list.getLength(); i++) {
					// Get element
					Element element = (Element) list.item(i);

					if ((element.getNodeName() == "Impression")) {
						evntsList.put("Impression",
								r.pullLinks(element.getTextContent()));
					}

					else if ((element.getNodeName() == "Tracking")) {

						evntsList.put(element.getAttribute("event"),
								r.pullLinks(element.getTextContent()));
					}
				}
				for (Map.Entry entry : evntsList.entrySet()) {
				
				    System.out.println(entry.getKey() + " ----> " + entry.getValue());
					System.out.println("");
				}
				

		} catch (Exception e) {
			System.exit(1);
		}
	}

}