package co.alexjo.examsquirrel.exam;

import co.alexjo.examsquirrel.data.DatabaseDriver;
import co.alexjo.examsquirrel.data.MongoDriver;
import co.alexjo.examsquirrel.data.PropertiesIO;
import java.util.ArrayList;
import org.bson.Document;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The ExamAPI BackEnd Application. The exam REST API
 * @author Alex Johnson
 */
@Path("exam")
public class ExamAPI {
    
    /** The database's address */
    public String address;
    /** The database's port */
    public int port;
    /** A map of the controllers properties */ 
    private Map<String, String> prop;
    
    /**
     * Constructs a new ExamAPI. A REST API that serves questions and 
     * exams to the front end.
     */
    public ExamAPI () {
        // load properties
        loadProperties();
        address = prop.get("db-address");
        port = Integer.parseInt(prop.get("db-port"));
    }
    
    /**
     * Loads the properties file
     */
    private void loadProperties () {
        prop = PropertiesIO.readPropertiesFile();
    }
    
    /**
     * Handles get requests for JSON exam data
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getExam() {
        DatabaseDriver db = new MongoDriver(address, port, prop.get("db-course"));
        String exam = Exam.create(db.getAll("math", "", ""), 20, 1);
        db.close();
        return exam + " ";
    }
    
    /**
     * Adds a question to a collection
     * @param json the exam object to store
     * @return the added object
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String addExam(String json) {
        
        Document doc = Document.parse(json);
        DatabaseDriver db = new MongoDriver(address, port, prop.get("db-course"));
        String id = doc.getString("id");
        String topic = doc.getString("topic");
        String content = doc.getString("content");
        ArrayList<String> choices = (ArrayList<String>) doc.getOrDefault("choices", new ArrayList<String>());
        ArrayList<String> tips = (ArrayList<String>) doc.getOrDefault("tips", new ArrayList<String>());
                
        Question q = new Question(id, topic, content, choices, tips, new double[1][2]);
        db.add(q, "math");
        
        return json;
    }
}
