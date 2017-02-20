package co.alexjo.examsquirrel.data;

import co.alexjo.examsquirrel.exam.Question;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;

/**
 * Wraps all MongoDatabase interactions to make migration to other Database
 * software less painful.
 * @author Alex Johnson
 */
public class MongoDriver implements DatabaseDriver {
    
    /** The Current Mongo Database connection */
    MongoDatabase dataBase;
    
    /**
     * Creates a new Mongo Driver
     * @param address the address of the database Server 
     * @param port the port of the database server
     * @param name the name of the database
     */
    public MongoDriver (String address, int port, String name) {
        dataBase = connect(address, port, name);
    }
    
    /**
     * Tries to connect to a MongoDB server at a specified port and address
     * @param address the address of the database server
     * @param port the port of the database server
     * @param name the name of the database in the server
     * @return the connected MongoDatabase
     * @throws RuntimeException if there is an error connecting to the server
     */
    private MongoDatabase connect (String address, int port, String name) {
        MongoClient client;
        try {
            client = new MongoClient(address, port);
        } catch (MongoException e) {
            throw new RuntimeException("Error connecting to server");
        }
        return client.getDatabase(name);
    }
    
    /**
     * Gets all of the Questions in a collection
     * @param collection the collection to query
     * @param name the name of the variable to match
     * @param value the value to match
     * @return an ArrayList of Questions
     */
    public ArrayList<Question> getAll (String collection, String name, String value) {
        ArrayList<Question> questions = new ArrayList<>();
                
        MongoCollection c = dataBase.getCollection(collection);
        
        try (MongoCursor<Document> cursor = c.find().iterator()) {
            while (cursor.hasNext()) { 
                Document o = cursor.next();
                String id = o.getString("id");
                String topic = o.getString("topic");
                String content = o.getString("content");
                ArrayList<String> choices = (ArrayList<String>) o.getOrDefault("choices", new ArrayList<String>());
                ArrayList<String> tips = (ArrayList<String>) o.getOrDefault("tips", new ArrayList<String>());
                
                questions.add(new Question(id, topic, content, choices, tips, new double[1][2]));
               
            }
        }
        
        return questions;
    }
    
    /**
     * Gets n number of the Questions in a collection
     * @param collection the collection to query
     * @param name the name of the variable to match
     * @param value the value to match
     * @param n the number of Documents to pick
     * @return an ArrayList of Questions
     */
    public ArrayList<Question> pick (String collection, String name, String value, int n) {
        ArrayList<Question> questions = getAll(collection, name, value);
        return questions;
    }
}
