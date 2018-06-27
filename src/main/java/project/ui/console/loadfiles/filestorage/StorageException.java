package project.ui.console.loadfiles.filestorage;

public class StorageException extends RuntimeException {

    static final long serialVersionUID = 97L;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
