package co.alexjo.examsquirrel;

import co.alexjo.examsquirrel.exam.EvalQuestion;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Alex Johnson
 */
@Path("exam")
public class ExamRequest {
    
    /** The course to use to generate exams */
    String course;
        
    /** 
     * Constructs a new ExamRequest 
     */
    public ExamRequest () {
        course = ExamGrind.py;
    }
    
    @GET
    @Produces("application/json")
    public String exam() {
        return course;
    }
    
    @PUT
    @Consumes("text/plain")
    public String help(String context) {
        return "HELLO";
    }
}
