package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.data.DatabaseDriver;
import co.alexjo.examsquirrel.data.MongoDriver;
import co.alexjo.examsquirrel.data.PropertiesIO;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * The SquirrelAPI BackEnd Application. The root of the rest API
 * @author Alex Johnson
 */
@Path("/exam")
public class SquirrelAPI {
    
    /** The course database */
    public DatabaseDriver courses;
    /** The users database */
    public DatabaseDriver users;
            
    private Map<String, String> prop;
    
    /**
     * Constructs a new ExamGrindAPI. A RESTful Api that serves questions, 
     * users and exams to the front end.
     */
    public SquirrelAPI () {
        
        // load properties
        loadProperties();
        
        String address = prop.get("db-address");
        int port = Integer.parseInt(prop.get("db-port"));
        System.out.println("Successfully connected to MongoDB on " + 
                address + ":" + port);
        
        setCourses(new MongoDriver(address, port, prop.get("db-course")));
        
    }
    
    /**
     * Loads the properties file
     */
    private void loadProperties () {
        prop = PropertiesIO.readPropertiesFile();
    }
    
    /**
     * Sets the Course database for ExamGrindAPI
     * @param database the course database
     */
    private void setCourses(DatabaseDriver database) {
        if (database == null) {
            throw new NullPointerException("Cannot set course database to null");
        }
        // Do other database validation checks
        courses = database;
    }
    
    /**
     * Sets the User database for ExamGrindAPI
     * @param database the course database
     */
    private void setUsers(DatabaseDriver database) {
        if (database == null) {
            throw new NullPointerException("Cannot set users database to null");
        }
        // Do other database validation checks
        users = database;
    }
    
    @GET
    @Produces("application/json")
    public String exam() {
        Course c = new Course(courses);
        return c.getExam(null, 20, 1);
    }
    
    @PUT
    @Consumes("text/plain")
    public String help(String context) {
        return "HELLO";
    }
}
