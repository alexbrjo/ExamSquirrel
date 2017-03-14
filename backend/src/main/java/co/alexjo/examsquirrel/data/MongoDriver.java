package co.alexjo.examsquirrel.data;

import co.alexjo.examsquirrel.exam.Question;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Wraps all MongoDatabase interactions. Handles the 2 major databases:
 *      - users
 *      - problems, sorted by creator (user)
 * @author Alex Johnson
 */
public class MongoDriver implements DatabaseDriver {
    
    /** The database connection */
    private MongoClient client;
    /** The Current Mongo Database connection */
    private MongoDatabase dataBase;
    
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
        try {
            client = new MongoClient(address, port);
        } catch (MongoException e) {
            throw new RuntimeException("Error connecting to server");
        }
        return client.getDatabase(name);
    }
    
    /**
     * Closes the database connection
     * @return if the connect was closed successfully
     */
    @Override
    public boolean close () {
        client.close();
        return true;
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
                
                questions.add(new Question(id, topic, content, choices, tips, new double[1][2], new int[3]));
               
            }
        }
        
        return questions;
    } 
    
    /**
     * Checks if a collection exists for a database
     * @param collection the name of the collection to test existence of
     * @return if the collection exists
     */
    @Override
    public boolean has(String collection) {
        Iterable<String> collections = dataBase.listCollectionNames();
        for (String name : collections) {
            if (collection.equalsIgnoreCase(name)) 
                return true;
        }
        return false;
    }
    
    /**
     * Adds a Question to a collection 
     */
    @Override
    public void add(Question question, String collection) {
        MongoCollection c = dataBase.getCollection(collection);
        Document doc = new Document();
        doc.append("id", question.getId());
        doc.append("topic", question.getId());
        doc.append("content", question.getId());
        doc.append("choices", question.getChoices());
        doc.append("tips", question.getTips());
        
        c.insertOne(doc);
    }
    
}
