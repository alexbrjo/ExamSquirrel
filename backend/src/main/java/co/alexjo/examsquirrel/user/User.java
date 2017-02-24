package co.alexjo.examsquirrel.user;

import java.util.ArrayList;

/**
 * A user in the SquirrelAPI system. All of these fields are preliminarily 
 * validated on the front end and validated with the same rules plus extras
 * of the server side. Must match pattern and some are checked to not have 
 * any of Google's blacklisted words.
 * @author Alex Johnson
 */
public class User {
    /**
     * Vital and unique account information
     */
    /** The Database id */
    private Long id;
    /** The user's username */
    private String username;
    /** The user's hashed password */
    private String hashedPassword;
    
    /**
     * Secondary account information. The user's self-description, their 
     * location and their web site.
     */
    /** The user's biography */
    private String description;
    /** The user's web site */
    private String website;
    /** The user's location */
    private String location;
    
    /**
     * The users exam data, both exams created and taken. 
     */
    /** The user's exams */
    private ArrayList<String> exams;
    /** The user's exam history */
    private ArrayList<String> history; 
    
    /**
     * Session and authentication data.
     */
    /** The last time the user logged in */
    private int lastLogin;
    
}
