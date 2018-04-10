package project.ui.console.loadfiles.loadprojects;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

public interface LoadProject {

    /**
     * Method that reads and parses project files to java objects
     *
     * @param filePath File to parse
     *
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws DOMException
     * @throws ParseException
     */
    public void loadProject(String filePath) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException;
}
