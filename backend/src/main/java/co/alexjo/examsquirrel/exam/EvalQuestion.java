package co.alexjo.examsquirrel.exam;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * A EvalQuestion evaluates a Question for a given seed. The actual question 
 * object used is altered in the method. The choices are scrambled 
 * using the seed as well.
 * @author Alex Johnson
 */
public class EvalQuestion {

    /** The script manager  */
    private static ScriptEngine js;
    
    /** Creates the script manager */
    static {
        ScriptEngineManager manager = new ScriptEngineManager();
        js = manager.getEngineByName("JavaScript");
    }
    
    public static String evalEmbedded (Question q, String container, int seed) throws QuestionFormatException {
        // if theres no script don't go on
        if (!container.contains("$")) {
            return container;
        }

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
                String script = "var a = " + getVar(0, seed, q) +  
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
    private static double getVar (int index, double seed, Question q) {
        double[][] fullVariation = q.getVariation();
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
        if (index < 0 || index >= q.getVariation().length) {
            throw new IllegalArgumentException("No variable for given index");
        }
        
        double[] variation = q.getVariation()[index];
        double from = variation[0];
        double raw = from + seed * (variation[1] - from);
        
        return Math.round((raw - (raw % variation[2])) * 10) / 10;
    }    
}
