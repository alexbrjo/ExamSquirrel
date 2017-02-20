package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.data.DatabaseDriver;
import co.alexjo.examsquirrel.exam.Exam;
import co.alexjo.examsquirrel.exam.Question;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import org.bson.json.JsonWriter;

/**
 *
 * @author Alex Johnson
 */
public class Course {
    /** All the Courses Topics */
    private ArrayList<Question> masterList;
    /** Questions sorted by topic */
    private HashMap<String, ArrayList<Question>> questionForTopic;
    
        
    public Course (DatabaseDriver dataBase) {
        masterList = dataBase.getAll("physics", "", "");
        questionForTopic = new HashMap();
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
        /*ArrayList<Question> include = new ArrayList<>();
        for (String m : material) {
            if (questionForTopic.containsKey(m)) {
                include.addAll(questionForTopic.get(m));
            }
        }*/
        
        // Create Writer
        StringWriter writer = new StringWriter();
        JsonWriter out = new JsonWriter(writer);
        
        // Write JSON
        out.writeStartDocument();
        Exam exam = new Exam(masterList.size(), masterList, seed);
        exam.print(out);
        out.writeEndDocument();
        
        return writer.toString();
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
