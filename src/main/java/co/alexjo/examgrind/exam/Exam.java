package co.alexjo.examgrind.exam;

import java.util.ArrayList;
import java.util.Random;

/**
 * An exam
 * @author Alex Johnson
 */
public class Exam {
    
    /** The maximum number of questions */
    private static final int MAX_QUESTIONS = 50;
    /** Number of questions */
    private int numberOfQuestions;
    /** Questions to use */
    ArrayList<Question> rawQuestions;
    /** Questions to use */
    ArrayList<EvalQuestion> questions;
    
    private int seed;
    
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
        generateExam(seed);
    }
    
    /**
     * Generates the Exam for a given seed
     * @param seed the seed for the Random
     */
    private void generateExam (int seed) {
        
        Random random = new Random(seed);
        
        // evaluate questions
        ArrayList<EvalQuestion> evaled = new ArrayList<>();
        for (int i = 0; i < numberOfQuestions; i++) {
            evaled.add(new EvalQuestion(rawQuestions.get(i), random.nextDouble()));
        }
        
        // scramble order of questions
        ArrayList<EvalQuestion> scrambled = new ArrayList<>();
        for (EvalQuestion q : evaled) {
            scrambled.add(Math.round( (float) random.nextDouble() * scrambled.size()), q);
        }
        
        questions = scrambled;
    }
    
    /**
     * Creates a JSON object (root is an Array) for the exam
     * @return a String of JSON that is the generated test
     */
    public String create() {
        String json = "[";
        for (int i = 0; i < questions.size(); i++) {
            EvalQuestion e = questions.get(i);
            json += e.generate(i) + ((i != questions.size() - 1) ? "," : "]");
        }
        return json;
    }
    
    /**
     * Creates a JSON object (root is an Array) for the exam for a given seed
     * @param seed the seed to use to generate the exam
     * @return a String of JSON that is the generated test
     */
    public String create(int seed) {
        generateExam(seed);
        return create();
    }
    
    /**
     * Sets the number of questions.
     * @param numberOfQuestions the number of questions
     * @throws IllegalArgumentException if the number of questions is less than 
     * 1 or greater than MAX_QUESTIONS.
     */
    private void setNumberOfQuestions (int numberOfQuestions) {
        if (numberOfQuestions < 0 || numberOfQuestions > MAX_QUESTIONS ||
                numberOfQuestions >= rawQuestions.size()) {
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
    
}
