package co.alexjo.examsquirrel.data;

import co.alexjo.examsquirrel.exam.Question;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
import org.bson.Document;

/**
 * Wraps all basic Database functionality. Ensures that migration to other 
 * Database software can be done with minimal effort.
 * @author Alex Johnson
 */
public class DatabaseDriver {
    
    MongoDatabase dataBase;
    
    public DatabaseDriver (String address, int port, String name) {
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
    
    public ArrayList<Question> get () {
        String s = "";
        ArrayList<Question> questions = new ArrayList<>();
        
        MongoCollection c = dataBase.getCollection("physics");
        
        try (MongoCursor<Document> cursor = c.find().iterator()) {
            while (cursor.hasNext()) { 
                Document o = cursor.next();
                // TODO
                String id = o.getString("id");
                String topic = o.getString("topic");
                String content = o.getString("content");
                ArrayList<String> choices = new ArrayList<>();
                ArrayList<String> tips = new ArrayList<>();
                double[][] variation = o.get("variation", double[][].class);
                
                questions.add(new Question(id, topic, content, choices, tips, variation));
            }
        }
        
        return questions;
    }
}
