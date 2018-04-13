package project.ui.console.loadfiles.loadprojects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoadProjectXmlFactory {


    @Autowired
    LoadProjectXmlv01 loadXmlv01;

    private static final Map<String, LoadProjectXmlv01> fileExtensions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
        fileExtensions.put("v01", loadXmlv01);
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
    public LoadProjectXmlv01 getReader(String fileName) {

        String xmlVersion = fileName.split("_")[1].trim();

        return fileExtensions.get(xmlVersion);

    }

}
