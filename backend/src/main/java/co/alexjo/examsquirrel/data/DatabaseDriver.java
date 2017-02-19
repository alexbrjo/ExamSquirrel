package co.alexjo.examsquirrel.data;

import co.alexjo.examsquirrel.exam.Question;
import java.util.ArrayList;

/**
 * Wraps all basic Database functionality. Ensures that migration to other 
 * Database software can be done with minimal effort.
 * @author Alex Johnson
 */
public interface DatabaseDriver {
    
    /**
     * Gets all the Questions that for the given JSON name matches the given 
     * pattern.
     * @param collection the collection to query
     * @param name the JSON name to match
     * @param pattern the Regex pattern that the value must match 
     * @return an ArrayList of all objects that match the pattern
     */
    public ArrayList<Question> getAll (String collection, String name, String pattern);
    
    /**
     * Gets all the Questions that for the given JSON name matches the given 
     * pattern.
     * @param collection the collection to query
     * @param name the JSON name to match
     * @param pattern the Regex pattern that the value must match 
     * @param n the number of Questions to get
     * @return the questions for 
     */
    public ArrayList<Question> pick (String collection, String name, 
            String pattern, int n);
}
