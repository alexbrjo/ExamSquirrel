package co.alexjo.examsquirrel.exam;

import co.alexjo.examsquirrel.exam.QuestionFormatException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests class QuestionFormatException.
 * @author Alex Johnson
 */
public class QuestionFormatExceptionTest {

    /**
     * Tests all constructors for QuestionFormatException
     */
    @Test
    public void testNewQuestionFormatException() {
        
        // test throw with default message
        Exception e1 = new QuestionFormatException();
        assertEquals("Default message not set correctly", "Question formatting error.", e1.getMessage());
        
        // test throw with custom message
        Exception e2 = new QuestionFormatException("Custom Message");
        assertEquals("Message not set correctly", "Custom Message", e2.getMessage());
        
    }
    
    /**
     * Tests throwing QuestionFormatException, more testing JVM, but checking 
     * that throws correctly nonetheless.
     */
    @Test
    public void testThrowQuestionFormatException() {
        
        // test throw with default message
        try {
            throw new QuestionFormatException();
        } catch (Exception e) {
            assertTrue("Exception throw; not QuestionFormatException", 
                    e instanceof QuestionFormatException);
            assertEquals("Default message not set correctly", "Question formatting error.", e.getMessage());
        }
        
        // test throw with custom message
        try {
            throw new QuestionFormatException("Error on line 53: <tag>");
        } catch (Exception e) {
            assertTrue("Exception throw; not QuestionFormatException", 
                    e instanceof QuestionFormatException);
            assertEquals("Message not set correctly", "Error on line 53: <tag>", e.getMessage());
        }
    }
    
}
