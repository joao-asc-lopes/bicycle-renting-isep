package lapr.project.utils;

public class InvalidDataException extends IllegalArgumentException {

    private static final long serialVersionUID = 123456; // This warning comes when you derive from a class that implements Serializable, so we gave the class a serialVersionUID

    /**
     * The default message when popping an InvalidDataException.
     */
    public InvalidDataException() {
        super("Invalid data!");
    }

    /**
     * The customizable message when popping an InvalidDataException.
     *
     * @param message The message passed as a parameter.
     */

    public InvalidDataException(String message) {
        super(message);
    }
}
