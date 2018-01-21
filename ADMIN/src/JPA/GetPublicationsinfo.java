package JPA;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class GetPublicationsinfo {

	static List<Publicationoficial> list;

	public static List<Publicationoficial> get() throws ParserConfigurationException, SAXException, IOException{
				
		File inputFile = new File("C:/Users/postgres/workspace/publicas/src/commonb/trabalho.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		
		list = new ArrayList<Publicationoficial>();
		doc.getDocumentElement().normalize();
		
		
		
		NodeList nList = doc.getElementsByTagName("Publication");
		

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);
			
			Publicationoficial publica = new Publicationoficial();
					
					
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				
				publica.settitle(eElement.getElementsByTagName("Title").item(0).getTextContent());
				publica.setauthors(eElement.getElementsByTagName("Authors").item(0).getTextContent());
				publica.setpubdate(eElement.getElementsByTagName("Publication_date").item(0).getTextContent());
				
			}
			list.add(publica);
	
			
		}
		
		
		return list;
		
	}
}

