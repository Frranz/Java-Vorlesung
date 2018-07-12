package src.com.company;
/*
* is thrown everytime an unregistered entity in the database file occurs
*
*
* */
public class FileValidityException extends Exception {

    public FileValidityException() {
        super();
    }

    public FileValidityException(String message) {
        super(message);
    }
}
