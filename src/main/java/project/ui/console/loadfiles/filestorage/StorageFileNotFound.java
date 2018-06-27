package project.ui.console.loadfiles.filestorage;

public class StorageFileNotFound extends StorageException {

    static final long serialVersionUID = 96L;

    public StorageFileNotFound(String message) {
        super(message);
    }

    public StorageFileNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}