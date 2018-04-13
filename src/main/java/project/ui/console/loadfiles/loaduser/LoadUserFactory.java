package project.ui.console.loadfiles.loaduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoadUserFactory {

    @Autowired
    LoadUserXmlVersionFactory loadUserXmlVersionFactory;
    @Autowired
    LoadUserCSV loadUserCSV;
    @Autowired
    LoadUserXmlVersion loadUserXmlVersion;

    private static final Map<String, LoadUser> fileExtensions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
        fileExtensions.put("XML", loadUserXmlVersion);
        fileExtensions.put("CSV", loadUserCSV);
    }

    public LoadUser getLoadUserType(String filePath){

        String fileExtension = filePath.split("\\.")[1].trim().toUpperCase();

        if (("XML").equals(fileExtension)){
            loadUserXmlVersionFactory.getLoadUserType(filePath);
        }

        return fileExtensions.get(fileExtension);
    }
}
