package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.exam.Exam;
import co.alexjo.examsquirrel.exam.Question;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Alex Johnson
 */
public class Course {
    /** The resources folder */
    private static final String RESOURCE_FOLDER = "physics/";
    /** The required properties file name */
    private static final String PROPERTIES_FILE = "properties.json";
    /** All the Courses Topics */
    private ArrayList<Question> masterList;
    /** Questions sorted by topic */
    private HashMap<String, ArrayList<Question>> questionForTopic;
    
        
    public Course () {
        File rsc = new File(ExamGrind.class.getClassLoader().getResource(RESOURCE_FOLDER).getFile());
        File[] files = rsc.listFiles();
        masterList = new ArrayList<>();
        questionForTopic = new HashMap();
        
        System.out.println("Course was successfully initialized with");
        System.out.println(masterList.size() + " questions loaded");
    }
    
    /**
     * Generates an Exam using a Courses question catalog
     * @param material the material to cover
     * @param numberOfQuestions the number of questions
     * @param seed the seed to use for generation
     * @return the JSON object that represents an exam
     */
    public String getExam (String[] material, int numberOfQuestions, int seed) {
        
        // Material to cover in the exam
        ArrayList<Question> include = new ArrayList<>();
        for (String m : material) {
            if (questionForTopic.containsKey(m)) {
                include.addAll(questionForTopic.get(m));
            }
        }
        
        //Exam exam = new Exam(numberOfQuestions, include, seed);
        Exam exam = new Exam(numberOfQuestions, masterList, seed);
        return exam.create();
    } 
    
    /**
     * Gets the total number of questions loaded
     * @return the total number of questions loaded
     */
    public int getNumberOfQuestions () {
        return masterList.size();
    }
    
    /**
     * 
     * @return the String of letter that are the answers to an exam
     */
    public String getExamKey () {
        return "AABACDDCBACAACEABDEA";
    }
}