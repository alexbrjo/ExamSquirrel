package co.alexjo.examgrind.exam;

/**
 * Is thrown if a JSON object doesn't have valid formatting.
 * @author Alex Johnson
 */
public class QuestionFormatException extends Exception{
    
    /** ID used for serialization */
    private static final long serialVersionUID = 1L;
    /** The default message of a ConflictException */
    private static final String DEFAULT_MESSAGE = "Question formatting error.";
    
    /**
     * Constructs a QuestionEvalException with the Default message
     */
    public QuestionFormatException () {
        this(DEFAULT_MESSAGE);
    }
    
    /**
     * Constructs a QuestionEvalException for a given message
     * @param message message of the Exception
     */
    public QuestionFormatException (String message) {
        super(message);
    }
}
