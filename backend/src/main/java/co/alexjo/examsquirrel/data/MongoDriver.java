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
    
    MongoDatabase dataBase;
    
    public MongoDriver (String address, int port, String name) {
        dataBase = connect(address, port, name);
    }
    
    /**
     * Tries to connect to a MongoDB server at a specified port and address
     * @param address
     * @param port
     * @param name
     * @return 
     */
    private MongoDatabase connect (String address, int port, String name) {
        MongoClient client;
        try {
            client = new MongoClient(address, port);
        } catch (MongoException e) {
            throw new RuntimeException();
        }
        return client.getDatabase(name);
    }
    
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
    
    public ArrayList<Question> pick (String collection, String name, String value, int n) {
        ArrayList<Question> questions = getAll(collection, name, value);
        return questions;
    }
}
