package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.data.DatabaseDriver;
import co.alexjo.examsquirrel.data.PropertiesIO;
import java.util.Map;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


/**
 * The SquirrelAPI BackEnd Application. The root of the rest API
 * @author Alex Johnson
 */
@ApplicationPath("")
public class SquirrelAPI extends Application {
    
    /** The course database */
    private DatabaseDriver courses;
    /** The users database */
    private DatabaseDriver users;
    
    public static String py;
    
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
        
        setCourses(new DatabaseDriver(address, port, prop.get("db-course")));
        py = courses.get();
        
        /*Collection<DB> databases = m.getUsedDatabases();
        System.out.println(m.toString());
        
        for (DB db : databases) {
            System.out.println("Found database " + db.getName());
            if ( db.getName().equals(COURSE_DB_NAME) ) {
                setCourses(db);
            }
        }*/
        
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
}
