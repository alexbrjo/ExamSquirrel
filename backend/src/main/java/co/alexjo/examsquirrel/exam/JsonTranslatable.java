package co.alexjo.examsquirrel.exam;

/**
 * Defines the methods a class needs to implement to be translatable into
 * JSON.
 * @author Alex Johnson
 */
public interface JsonTranslatable {
    /**
     * Prints an object to a JsonWriter
     * @param out the JSON writer to print the object to
     */
    public void print (org.bson.json.JsonWriter out);
}
