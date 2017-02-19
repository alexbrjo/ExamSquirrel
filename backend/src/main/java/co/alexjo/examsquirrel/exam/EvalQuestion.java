package co.alexjo.examsquirrel.exam;

import java.io.PrintWriter;
import java.util.ArrayList;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.bson.json.JsonWriter;

/**
 * A EvalQuestion is a Question that is given a seed. Upon construction all
 * embedded scripts are evaluated using the seed. The choices are scrambled 
 * using the seed as well.
 * @author Alex Johnson
 */
public class EvalQuestion extends Question {
    
    /** The text of the question */
    private String evalContent;
    /** The choices of the question, index 0 is the answer */
    private ArrayList<String> evalChoices;
    
    /** Variation variables from seed */
    private double[] vars;
    /** The answer of the question */
    private char answer;
    
    private double personalRandom;
    
    /**
     * Creates an EvalQuestion for a question.
     * @param q 
     */
    public EvalQuestion (Question q) {
        this(q, 0);
    }
    
    /**
     * Creates an EvalQuestion for a question and seed.
     * @param q the question to create
     * @param seed the next random number from the program's random
     */
    public EvalQuestion (Question q, double seed) {
        super(q.getId(), q.getTopic(), q.getContent(), q.getChoices(), 
                q.getTips(), q.getVariation());
        this.personalRandom = seed;
        eval();
    }
    
    /**
     * The answer of the seed
     * @param seed the seed that is used to find the answer
     * @return the int of the answer choice 0 - A, 1 - B, etc
     */
    public static int getAns (double seed) {
        /*if (seed < 0 || seed > 1) {
            throw new IllegalArgumentException("Invalid double, must be 0 through 1");
        }
        String a = "ABCDE";
        for (int i = 0; i < 3; i++) {
            String org = a.substring(i);
        }
        return a.indexOf("A");*/
        return 0;
    }
    
    /**
     * Creates an 
     */
    private void eval () {
        try {
            ArrayList<String> initial = getChoices();
            ArrayList<String> evaledChoices = new ArrayList<>();
        
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine js = manager.getEngineByName("JavaScript");

            evalContent = evalEmbedded(getContent(), js);

            for (int i = 0; i < initial.size(); i++) {
//                evalChoices.add(evalEmbedded (initial.get(i), js));
            }
            
        } catch (QuestionFormatException e) {
            throw new IllegalArgumentException("Invalid format for question " + getId());
        } 
    } 
    
    public String evalEmbedded (String container, ScriptEngine js) throws QuestionFormatException {
        // search entire String for scripts
        for (int i = 0; i < container.length(); i++) {
            // if start of script 
            if (container.charAt(i) == '$' && container.charAt(i + 1) == '{') {
                // determine content of the script
                int start = i + 1;
                int end = i + 1;
                boolean scopeDetermined = false;
                while (!scopeDetermined) {
                    end++;
                    scopeDetermined = container.charAt(end) == '}';
                }
               
                // Check that script isn't empty
                String contentOf = container.substring(start + 1, end);
                if (contentOf.length() == 0) throw new QuestionFormatException("Empty script container");
                
                
                // Get script and add variation variables
                String script = "var a = " + getVar(0, personalRandom) +  
                        "; " + contentOf;
                        
                String eval = "";
                try {
                    eval = js.eval(script).toString();
                } catch (ScriptException e) {
                    throw new QuestionFormatException("JavaScript syntax error");
                }
                
                container = container.substring(0, start - 1) + eval + 
                        container.substring(end + 1);
                
            }
        }
        
        return container;
    }
    
    /**
     * Get a variable from index with variation from a seed.
     * @param index the index of the variable to get from the 
     * @param seed the seed to obtain the number from
     * @return gets a varied variable 
     */
    public double getVar (int index, double seed) {
        double[][] fullVariation = getVariation();
        if (fullVariation.length == 0) {
            return -1;
        } else {
            for (double[] d : fullVariation) {
                if (d.length < 3) {
                    return -1;
                }
            }
        }
        
        if (seed < 0 || seed > 1) {
            throw new IllegalArgumentException("Invalid double, must be 0 through 1");
        }
        if (index < 0 || index >= getVariation().length) {
            throw new IllegalArgumentException("No variable for given index");
        }
        
        double[] variation = getVariation()[index];
        double from = variation[0];
        double raw = from + seed * (variation[1] - from);
        
        return Math.round((raw - (raw % variation[2])) * 10) / 10;
    }
    
    /**
     * Writes Json for an Evaluated question. Uses Json writer instead of 
     * mapping library to better control what the user gets to see. Writes the 
     * Question variables id, topic and tips and the EvalQuestion variables
     * content, choices and variation.
     * @param out the JsonWriter to write to
     */
    public void print (JsonWriter out) {
        // start the Question object
        out.writeStartDocument();
        
        // The unqiue id of the question, NEVER evaluated
        out.writeName("id");
        out.writeString(getId());
        
        // The topic of the question, NEVER evaluated
        out.writeName("topic");
        out.writeString(getTopic());
        
        // The content of the question, ALWAYS evaluated
        out.writeName("content");
        out.writeString(evalContent);
        
        // The choices of the question, ALWAYS evaluated
        out.writeName("choices");
        out.writeStartArray();
        for (String choice : getChoices()) {
            out.writeString(choice);
        }
        out.writeEndArray();
        
        // The tips of the question, NEVER evaluated
        out.writeName("tips");
        out.writeStartArray();
        for (String tip : getTips()) {
            out.writeString(tip);
        }
        out.writeEndArray();
        
        // The variation of the question, ALWAYS evaluated
        out.writeName("variation");
        out.writeStartArray();
        for (double[] variable : getVariation()) {
            out.writeStartArray();
            for (double d : variable) {
                out.writeDouble(d);
            }
            out.writeEndArray();
        }
        out.writeEndArray();
        
        // Ends the Question object
        out.writeEndDocument();
        
    }
    
}