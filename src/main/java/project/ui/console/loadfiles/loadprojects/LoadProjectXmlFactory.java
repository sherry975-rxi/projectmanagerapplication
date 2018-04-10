package project.ui.console.loadfiles.loadprojects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoadProjectXmlFactory {


    @Autowired
    LoadProjectXmlv00 loadXmlv00;

    private static final Map<String, LoadProjectXmlv00> fileExtensions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
        fileExtensions.put("v00", loadXmlv00);
    }


    /**
     * This static class receives a filePath and gets the file extesion in order to decide
     * which LoadProject has to instantiate

     * @param fileName File to load
     *
     * @return New LoadProject implementation correspondent to the file extension
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public LoadProjectXmlv00 getReader(String fileName) throws InstantiationException, IllegalAccessException {

        String xmlVersion = fileName.split("_")[1].trim();

        return fileExtensions.get(xmlVersion);

    }

}
