package project.ui.console.loadfiles.loadprojects;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class LoadProjectFactory {

    @Autowired
    LoadProjectXml loadXml;

    private static final Map<String, LoadProject> fileExtensions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
       fileExtensions.put("XML", loadXml);
    }


    /**
     * This static class receives a filePath and gets the file extesion in order to decide
     * which LoadProject has to instantiate

     * @param filePath File to load
     *
     * @return New LoadProject implementation correspondent to the file extension
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public LoadProject getLoadProjectType(String filePath) throws InstantiationException, IllegalAccessException {

        //Convert to Enum
        String fileExtension = filePath.split("\\.")[1].trim().toUpperCase();

        return fileExtensions.get(fileExtension);

    }

}
