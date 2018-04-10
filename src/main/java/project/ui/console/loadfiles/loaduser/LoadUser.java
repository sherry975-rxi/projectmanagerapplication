package project.ui.console.loadfiles.loaduser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface LoadUser {

    void usersReader(String filePath) throws ParserConfigurationException, SAXException, IOException;
}
