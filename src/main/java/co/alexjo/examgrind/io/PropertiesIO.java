package co.alexjo.examgrind.io;

import co.alexjo.examgrind.Properties;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads Course Properties from a json file.
 * @author Alex Johnson
 */
public class PropertiesIO {
            
    public PropertiesIO () {
        throw new IllegalArgumentException("PropertiesIO is only static methods "
                + "and fields, not intended to be constructed.");
    }
    
    public static Properties readPropertiesFile(File file) {
        
        FileReader in;
        
        try {
            in = new FileReader(file);
        } catch (IOException e) {
            return null;
        }
        
        Gson gson = new Gson();
        Properties prop = gson.fromJson(in, Properties.class);
        
        return prop;
    }
}
