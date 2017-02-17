package co.alexjo.examsquirrel.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Reads Course Properties from the config.prop file. The configuration file 
 * exists so that the administrator can make changes to the application without 
 * having to update and recompile the course code.
 * @author Alex Johnson
 */
public class PropertiesIO {
    /*
        PropertiesIO constants, mostly validation variables
    */
    /** The filename of the properties file */        
    private static final String PROP_FILENAME = "config.prop";
    /** The maximum address length that won't send a warning */
    private static final int WARN_ADDRESS_LENGTH = 26;
    /** The smallest ICANN port */
    private static final int ICANN_MIN_PORT = 1024;
    /** The largest ICANN port */
    private static final int ICANN_MAX_PORT = 49151;
    /** The largest possible port */
    private static final int MAX_PORT = 65535;
    
    /*
        Defaults for property values
    */
    /** The default database address */
    private static final String DB_ADDRESS = "localhost";
    /** The default database port */
    private static final String DB_PORT = "27017";
    /** The default database name for the course database */
    private static final String COURSE_DB_NAME = "EG_courses";
    /** The default database name for the course database */
    private static final String USERS_DB_NAME = "EG_users";
    /** If the Application should suppress warnings */
    private static final String SUPPRESS_WARNINGS = "false";
    
    /**
     * Prevents the construction of a PropertiesIO instance, which would be 
     * pretty useless.
     */
    public PropertiesIO () {
        throw new IllegalArgumentException("PropertiesIO is only static methods "
                + "and fields, not intended to be constructed.");
    }
    
    /**
     * Reads a properties file and returns a map of values. Preforms validation
     * of values and assigns defaults for values not set.
     * @return a map of property values and keys
     * @throws IllegalArgumentException if the file could not be read
     */
    public static Map<String, String> readPropertiesFile() {
        // Determine resoures folder and where properties probably is
        String path = PropertiesIO.class.getClassLoader().getResource(PROP_FILENAME).getFile();
        File config = new File(path);
        // Create scanner and defauly properties
        Scanner in = null;
        Map<String, String> values = generateDefaults();
        
        // Try to create scanner to read properties file
        try {
            in = new Scanner (config);
        } catch (IOException e) {
            throw new IllegalArgumentException("Properties file could not be loaded");
        }
        
        // test each line of file
        while (in.hasNextLine()) {
            String line = in.nextLine().replaceAll(" ", "");
            // skips commented lines
            if (line.startsWith("#")) { 
                continue;
            }
            
            // Adds values for each line and skips if its an invalid line
            try { 
                String[] nameAndValue = line.split("=");
                String name = nameAndValue[0].replaceAll(" ", "");
                String value = nameAndValue[1].replaceAll(" ", "");
                values.put(name, value);
            } catch (IndexOutOfBoundsException e) {
                // skip line
            }
        }
        
        // tie our lose ends
        in.close(); 
        // do a quick check that the Config won't crash the application and return
        return validate(values); 
    }
    
    /**
     * Generates the default properties 
     */
    private static Map<String, String> generateDefaults() {
        HashMap<String, String> defaults = new HashMap<>();
        
        defaults.put("db-address", DB_ADDRESS);
        defaults.put("db-port", DB_PORT);
        defaults.put("db-course", COURSE_DB_NAME);
        defaults.put("db-users", USERS_DB_NAME);
        defaults.put("supress-warnings", SUPPRESS_WARNINGS);
        
        return defaults;
    }
    
    /**
     * Validates all the properties used by the app. Tries to catch errors 
     * so they don't crash the application later.
     * @param prop the Map of properties to validate
     */
    private static Map<String, String> validate(Map<String, String> prop) {
        
        // Warn if address is unreasonable
        if (prop.get("db-address").length() > WARN_ADDRESS_LENGTH) {
            System.out.println("Warning: Database address is above " + WARN_ADDRESS_LENGTH + " characters, OK.");
        }
        
        // Is port an integer
        int port = 0;
        try {
            port = Integer.parseInt(prop.get("db-port"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid port: not a number, reverting to default.");
            prop.put("db-port", DB_PORT);
        }
        
        // If port is completed invalid
        if (port < 0 || port > MAX_PORT) {
            System.out.println("Invalid port: out of range (0-65535), reverting to default.");
            prop.put("db-port", DB_PORT);
        }
        // Issues port warning if an irregular port
        if (port < ICANN_MIN_PORT || port > ICANN_MAX_PORT) {
            System.out.println("Warning: port not in the ICANN registered port range (1024-49151) OK.");
        }
        
        return prop;
    }
    
    /**
     * Creates a properties file with all the defaults
     * @throws IllegalArgumentException if there's any error creating the file.
     */
    public static void writeDefaultPropertiesFile () {
        String path = PropertiesIO.class.getClassLoader().getResource(PROP_FILENAME).getFile();
        File config = new File(path);
        
        // If config doesn't already exist
        if (!config.exists()) {
            // Try creating the file
            PrintStream out = null;
            try {
                out = new PrintStream(config);
            } catch (FileNotFoundException e) {
                throw new IllegalArgumentException("Exception writing file " + e);
            }
            
            // print header
            out.println("# Default config file ");
            
            // print name and value for each default
            Map<String, String> prop = generateDefaults();
            for (String key : prop.keySet()) {
                out.println(key + "=" + prop.get(key));
            }
            
        } else {
            // Log and don't do anything
            System.out.println("Config file already exists.");
        }
    }
}
