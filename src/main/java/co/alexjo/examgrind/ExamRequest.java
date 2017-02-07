package co.alexjo.examgrind;

import co.alexjo.examgrind.exam.EvalQuestion;
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
    Course course;
        
    /** 
     * Constructs a new ExamRequest 
     */
    public ExamRequest () {
        course = new Course();
    }
    
    @GET
    @Produces("application/json")
    public String exam() {
        String[] s = {""};
        return course.getExam(s, 20, 1);
    }
    
    @PUT
    @Consumes("text/plain")
    public String help(String context) {
        return "HELLO";
    }
}
