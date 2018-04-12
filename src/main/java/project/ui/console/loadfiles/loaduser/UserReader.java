package project.ui.console.loadfiles.loaduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import project.services.UserService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Service
public class UserReader {


    UserService uerService;


    LoadUserFactory creator;

    private UserReader(){

    }

    @Autowired
    public UserReader(UserService userService, LoadUserFactory creator) {
        this.uerService = userService;
        this.creator = creator;
    }

    /**
     * Instantiates a LoadUser strategy with the LoadUserFactory
     *
     *
      * @param file file to read
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public void readFile(String file) throws ParserConfigurationException, SAXException, IOException {
        LoadUser reader = creator.getLoadUserType(file);
        reader.usersReader(file);
    }

}
