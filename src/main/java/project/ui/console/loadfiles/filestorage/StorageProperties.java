package project.ui.console.loadfiles.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "uploadFiles";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
