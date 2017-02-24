package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.data.DatabaseDriver;
import co.alexjo.examsquirrel.data.MongoDriver;
import co.alexjo.examsquirrel.data.PropertiesIO;
import co.alexjo.examsquirrel.exam.Exam;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * The SquirrelAPI BackEnd Application. The exam REST API
 * @author Alex Johnson
 */
@Path("/exam")
public class SquirrelAPI {
    
    /** The database's address */
    public String address;
    /** The database's port */
    public int port;
    /** A map of the controllers properties */ 
    private Map<String, String> prop;
    
    /**
     * Constructs a new ExamGrindAPI. A REST Api that serves questions and 
     * exams to the front end.
     */
    public SquirrelAPI () {
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
    @Produces("application/json")
    public String exam() {
        DatabaseDriver db = new MongoDriver(address, port, prop.get("db-course"));
        String exam = Exam.create(db.getAll("physics", "", ""), 20, 1);
        db.close();
        return exam;
    }
    
    // not implemented
    @PUT
    @Consumes("text/plain")
    public String help(String context) {
        return "HELLO";
    }
}
