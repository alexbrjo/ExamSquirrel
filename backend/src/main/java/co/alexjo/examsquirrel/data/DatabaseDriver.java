package co.alexjo.examsquirrel.data;

import co.alexjo.examsquirrel.exam.Question;
import java.util.ArrayList;

/**
 * Wraps all basic Database functionality. Ensures that migration to other 
 * Database software will not be extremely painful.
 * @author Alex Johnson
 */
public interface DatabaseDriver {
    
    /**
     * Checks if a collection exists for a database
     * @param collection the name of the collection to test existence of
     * @return if the collection exists
     */
    public boolean has(String collection);
    
    /**
     * Closes the database connection
     * @return if there is not a database connection
     */
    public boolean close ();
    
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
     * Adds a question to a collection
     * @param question the question to add 
     * @param collection the name of the collection to add to 
     * @return if the collection exists
     */
    public void add(Question question, String collection);

}
