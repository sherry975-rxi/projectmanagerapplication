/**
 * 
 */
package project.ui.console.loadFile;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * @author Group3
 *
 */
public class FileUtils {

	public static Document readFromXmlFile(String pathFile)
			throws ParserConfigurationException, SAXException, IOException {

		File file = new File(pathFile);
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(file);

		return document;
	}

}
