package co.alexjo.examsquirrel.exam;

import co.alexjo.examsquirrel.exam.Question;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Tests the class Question. Question is a POJO that does not have data at 
 * time of creation, so this only checks that getters and setter work correctly, 
 * throw errors for nulls or other invalid values and that it can be 
 * constructed correctly.
 * @author Alex Johnson
 */
public class QuestionTest {
    
    /** A valid id */
    private String ID = "FFFFFFFF";
    /** A valid topic */
    private String TOPIC = "Algebra/Exponents";
    /** A valid CONTENT */
    private String CONTENT = "What is ${a}<sup>${b}<sup> + ${c}";
    /** The choices of the question, index 0 is the answer */
    private ArrayList<String> CHOICES = new ArrayList<>();
    /** Tips for solving the question */
    private ArrayList<String> TIPS = new ArrayList<>();
    /** The lowest number to vary the question from */
    private double[][] VARIATION;
    
    /**
     * Sets up default arrays
     */
    @Before 
    public void setUp () {
        // Add choices, TODO fix error of conflicting error evaluation
        CHOICES.add("${Math.pow(a, b) + c}");
        CHOICES.add("${a * b + c}");
        CHOICES.add("${a * b + c}");
        CHOICES.add("${a * b + c}");
        TIPS.add("asdasdf");
        
        // Add threes variabelse for variation
        VARIATION = new double[3][3];
        VARIATION[0] = new double[]{ 0.0, 10.0, 1.0}; // 0, 1, 2 ... 10
        VARIATION[1] = new double[]{ 2.0,  3.0, 1.0}; // 2, 3
        VARIATION[2] = new double[]{10.0, 20.0, 0.1}; // 10.0, 10.1, 10.2 ... 19.9, 20.0
    }
    
    /**
     * Test constructing a Question 
     */
    @Test
    public void testNewQuestion() {
        
        Question q1  = new Question();
        assertNotNull("Question equals null", q1);
        assertNull("ID was initialized", q1.getId());
        assertNull("Topic was initialized", q1.getTopic());
        assertNull("Content was initialized", q1.getContent());
        assertNotNull("Content wasn't initialized", q1.getChoices());
        assertNotNull("Tips wasn't initialized", q1.getTips());
        assertNotNull("Variation wasn't initialized", q1.getVariation());
        
        Question q2  = new Question();
        assertNotNull("Question equals null", q1);
        assertNull("ID was initialized", q1.getId());
        assertNull("Topic was initialized", q1.getTopic());
        assertNull("Content was initialized", q1.getContent());
        assertNotNull("Content wasn't initialized", q1.getChoices());
        assertNotNull("Tips wasn't initialized", q1.getTips());
        assertNotNull("Variation wasn't initialized", q1.getVariation());
        
    }

    /**
     * Test of getId method, of class Question.
     */
    @Test
    public void testGetId() {
        // Test getting a valid Id
        Question q1  = new Question();
        q1.setId(ID);
        assertEquals("Id modified value set", ID, q1.getId());
        assertNull("Topic was initialized", q1.getTopic());
        assertNull("Content was initialized", q1.getContent());
        assertNotNull("Content wasn't initialized", q1.getChoices());
        assertNotNull("Tips wasn't initialized", q1.getTips());
        assertNotNull("Variation wasn't initialized", q1.getVariation());
        
        Question q2  = new Question();
        q2.setId("ABCD1234");
        assertEquals("Id modified value set", ID, q2.getId());
        assertNull("ID was initialized", q2.getId());
        assertNull("Topic was initialized", q2.getTopic());
        assertNull("Content was initialized", q2.getContent());
        assertNotNull("Content wasn't initialized", q2.getChoices());
        assertNotNull("Tips wasn't initialized", q2.getTips());
        assertNotNull("Variation wasn't initialized", q2.getVariation());
    }

    /**
     * Test of getTopic method, of class Question.
     */
    @Test
    public void testGetTopic() {
        
    }

    /**
     * Test of getContent method, of class Question.
     */
    @Test
    public void testGetContent() {
        
    }

    /**
     * Test of getChoices method, of class Question.
     */
    @Test
    public void testGetChoices() {
        
    }

    /**
     * Test of getTips method, of class Question.
     */
    @Test
    public void testGetTips() {
        
    }

    /**
     * Test of getVariation method, of class Question.
     */
    @Test
    public void testGetVariation() {
        
    }

    /**
     * Test of setTopic method, of class Question.
     */
    @Test
    public void testSetTopic() {
        
    }

    /**
     * Test of setContent method, of class Question.
     */
    @Test
    public void testSetContent() {
        
    }

    /**
     * Test of addChoice method, of class Question.
     */
    @Test
    public void testAddChoice() {
    }

    /**
     * Test of addTip method, of class Question.
     */
    @Test
    public void testAddTip() {
        
    }

    /**
     * Test of setVariation method, of class Question.
     */
    @Test
    public void testSetVariation() {
       
    }

    /**
     * Test of toString method, of class Question.
     */
    @Test
    public void testToString() {
        
    }
    
}
