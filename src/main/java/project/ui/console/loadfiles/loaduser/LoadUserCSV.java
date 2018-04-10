package project.ui.console.loadfiles.loaduser;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class LoadUserCSV implements LoadUser{
    @Override
    public void usersReader(String filePath) throws ParserConfigurationException, SAXException, IOException {
        System.out.println("CSV reader isn't implemented yet");
    }
}
