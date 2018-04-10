package project.ui.console.loadfiles.loaduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoadUserXmlVersionFactory {

    @Autowired
    LoadUserXmlVersion1 loadUserXmlVersion1;

    private static final Map<String, LoadUserXmlVersion> xmlVersions = new HashMap<>();

    @PostConstruct
    public void initFileExtensions() {
        xmlVersions.put("v00", loadUserXmlVersion1);
    }

    public LoadUserXmlVersion getLoadUserType(String filePath){

        String fileVersion = filePath.split("_")[1].trim();

        return xmlVersions.get(fileVersion);
    }
}
