package project.ui.console.loadfiles.loadprojects;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

public interface LoadProjectXml extends LoadProject {

    void readProjectFile(String filePath) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException;
}
