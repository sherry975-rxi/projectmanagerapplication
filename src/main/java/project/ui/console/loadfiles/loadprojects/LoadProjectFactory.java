package project.ui.console.loadfiles.loadprojects;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class LoadProjectFactory {

    @Autowired
    LoadProjectXmlFactory loadProjectXmlFactory;

    @Autowired
    LoadProjectXml loadProjectXml;

    @Autowired
    LoadProjectCsv loadProjectCsv;

    private static final Map<String, LoadProject> fileExtensions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
       fileExtensions.put("XML", loadProjectXml);
       fileExtensions.put("CSV", loadProjectCsv);
    }


    /**
     * This static class receives a filePath and gets the file extension in order to decide
     * which LoadProject has to instantiate

     * @param filePath File to load
     *
     * @return New LoadProject implementation correspondent to the file extension
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public LoadProject getReader(String filePath){


        String fileExtension = filePath.split("\\.")[1].trim().toUpperCase();
        String fileName = filePath.split("\\.")[0].trim();

        if("XML".equals(fileExtension)) {
            return loadProjectXmlFactory.getReader(fileName);
        }

        return fileExtensions.get(fileExtension);

    }
}
