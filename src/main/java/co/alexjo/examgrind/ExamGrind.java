package co.alexjo.examgrind;

import com.mongodb.MongoClient;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


/**
 * Returns an exam for a request.
 * @author Alex Johnson
 */
@ApplicationPath("")
public class ExamGrind extends Application {
    
    public ExamGrind () {
        
    }
    
}
