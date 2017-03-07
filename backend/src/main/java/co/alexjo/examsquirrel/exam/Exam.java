package co.alexjo.examsquirrel.exam;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;
import org.bson.json.JsonWriter;

/**
 * An exam
 * @author Alex Johnson
 */
public class Exam implements JsonTranslatable {
    
    /** The maximum number of questions */
    private static final int MAX_QUESTIONS = 50;
    /** Number of questions */
    private int numberOfQuestions;
    /** Questions to use */
    ArrayList<Question> rawQuestions;
    /** Questions to use */
    ShuffledList<EvalQuestion> questions;
    /** The seed of the random */
    private int seed;
    /** The prime number used to create all the answer hashes */
    private int prime = 53;
    
    /**
     * Constructs a new exam. For a certain number of questions and topics 
     * to cover.
     * @param numberOfQuestions number of questions to generate
     * @param questions which topics to generate questions on
     */
    public Exam (int numberOfQuestions, ArrayList<Question> questions) {
        this(numberOfQuestions, questions, 0);
    }
    
    /**
     * Constructs a new exam. For a certain number of questions, topics 
     * to cover and a seed.
     * @param numberOfQuestions number of questions to generate
     * @param questions which topics to generate questions on
     * @param seed the random number seed
     */
    public Exam (int numberOfQuestions, ArrayList<Question> questions, int seed) {
        rawQuestions = questions;
        setNumberOfQuestions(numberOfQuestions);
        setSeed(seed);
    }
    
    /**
     * Generates the Exam for a given seed
     * @param seed the seed for the Random
     */
    private void generateExam (int seed) {
        
        Random random = new Random(seed);
        
        // evaluate questions
        ShuffledList<EvalQuestion> evaled = new ShuffledList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            evaled.add(new EvalQuestion(rawQuestions.get(i), random.nextDouble(), prime));
        }
        
        questions = evaled;
    }
    
    /**
     * Creates a JSON object (root is an Array) for the exam for a given seed
     */
    @Override
    public void print(JsonWriter out) {
        
        generateExam(seed);
        out.writeName("prime");
        out.writeInt32(prime);
        
        out.writeName("questions");
        out.writeStartArray();
        for (EvalQuestion e : questions) {
            e.print(out);
        }
        out.writeEndArray();
    }
    
    /**
     * Sets the number of questions.
     * @param numberOfQuestions the number of questions
     * @throws IllegalArgumentException if the number of questions is less than 
     * 1 or greater than MAX_QUESTIONS.
     */
    private void setNumberOfQuestions (int numberOfQuestions) {
        if (numberOfQuestions < 0 || numberOfQuestions > MAX_QUESTIONS ||
                numberOfQuestions > rawQuestions.size()) {
            throw new IllegalArgumentException("Invalid number of questions");
        }
        this.numberOfQuestions = numberOfQuestions;
    }
    
    /**
     * Sets the exam seed.
     * @param seed the seed
     */
    private void setSeed (int seed) {
        this.seed = seed;
    }
    
    /**
     * Generates an Exam using a Courses question catalog
     * @param list the material to cover
     * @param numberOfQuestions the number of questions
     * @param seed the seed to use for generation
     * @return the JSON object that represents an exam
     */
    public static String create (ArrayList<Question> list, int numberOfQuestions, int seed) {
        
        // Create Writer
        StringWriter writer = new StringWriter();
        JsonWriter out = new JsonWriter(writer);
        
        // Write JSON
        out.writeStartDocument();
        Exam exam = new Exam(list.size(), list, seed);
        exam.print(out);
        out.writeEndDocument();
        
        return writer.toString();
    } 
    
}
