package co.alexjo.examsquirrel.data;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Wraps all basic Database functionality. Ensures that migration to other 
 * Database software can be done with minimal effort.
 * @author Alex Johnson
 */
public class DatabaseDriver {
    
    MongoDatabase dataBase;
    
    public DatabaseDriver (String address, int port, String name) {
        MongoClient m = new MongoClient(address, port);
        dataBase = m.getDatabase(name);
    }
    
    public String get () {
        String s = "";
        MongoCollection c = dataBase.getCollection("physics");
        
        MongoCursor<Document> cursor = c.find().iterator();
        try {
            while (cursor.hasNext()) {
                s += cursor.next().toJson();
            }
        } finally {
            cursor.close();

        }
        
        return s;
    }
}
