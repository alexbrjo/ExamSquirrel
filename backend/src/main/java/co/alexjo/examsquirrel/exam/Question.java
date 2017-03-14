package co.alexjo.examsquirrel.exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.json.JsonWriter;

/**
 * The Question document structure from a database. Has unevaluated Question
 * data.
 * 
 * TODO
 *      override .equals()
 *      override .toHashCode()
 * 
 * @author Alex Johnson
 */
public class Question implements JsonTranslatable {
    
    /** The id of the question */
    private String id;
    /** The topic of the question */
    private String topic;
    
    /** The text of the question */
    private String content;
    /** The choices of the question, index 0 is the answer */
    private List<String> choices;
    /** Tips for solving the question */
    private List<String> tips;
    
    /** The limits and interval of variation */
    private double[][] variation;
    /** The answer of the question */
    private int[] answer;
    
    /**
     * Creates a 
     * @param id The unique identification string of the question
     * @param topic The topic of the question
     * @param content The text of the question
     * @param choices The choices of the question, index 0 is the answer
     * @param tips Tips for solving the question
     * @param variation The lowest number to vary the question from
     */
    public Question (String id, String topic, String content, List<String> choices, 
            List<String> tips, double[][] variation, int[] answer) {
        super();
        
        setId(id);
        setTopic(topic);
        setContent(content);
        setChoices(choices);
        setTips(tips);
        setVariation(variation);
        setAnswer(answer);
    }
    
    /**
     * Evaluates the content of the question and the answer choices. 
     * @param q
     * @param seed
     * @param prime
     * @return 
     */
    public void eval (int seed, int prime) {
        String evalContent = "";
        List<String> evalChoices = new ArrayList<>();
        int[] evalAnswer = new int[1];
        
        try {
            List<String> initial = choices;

            // evaluate the content
            evalContent = EvalQuestion.evalEmbedded(this, content, seed);

            // evaluate each answer choice
            String ans = "";
            for (int i = 0; i < initial.size(); i++) {
                String eval = EvalQuestion.evalEmbedded (this, initial.get(i), seed);
                if (i == 0)
                    ans = eval;
                evalChoices.add(eval);
            }
            
            // find index of answer in shuffled choices
            String str = id + (char)('A' + evalChoices.indexOf(ans));
            int hash = 0;
            for (int i = 0; i < str.length(); i++) {
                hash += str.charAt(i) * prime;
            }
            evalAnswer = new int[1];
            evalAnswer[0] = hash & hash;
            
        } catch (QuestionFormatException e) {
            throw new IllegalArgumentException("Invalid format for question " + id);
        }
        
        setContent(evalContent);
        setChoices(evalChoices);
        setAnswer(evalAnswer);
    } 
    
    /**
     * Prints a question object to Json.
     * @param out the writer to print to
     */
    @Override
    public void print(JsonWriter out) {
        // start the Question object
        out.writeStartDocument();
        
        // The unqiue id of the question
        out.writeName("id");
        out.writeString(id);
        
        // The topic of the question
        out.writeName("topic");
        out.writeString(topic);
        
        // The content of the question
        out.writeName("content");
        out.writeString(content);
        
        // The choices of the question
        out.writeName("choices");
        out.writeStartArray();
        for (String choice : choices) {
            out.writeString(choice);
        }
        out.writeEndArray();
        
        // The answer(s) of the questions
        out.writeStartArray();
        for (int i : answer) {
            out.writeInt32(i);
        }
        out.writeEndArray();
        
        // The tips of the question
        out.writeName("tips");
        out.writeStartArray();
        for (String tip : tips) {
            out.writeString(tip);
        }
        out.writeEndArray();
        
        // The variation of the question
        out.writeName("variation");
        out.writeStartArray();
        for (double[] variable : variation) {
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
    
    /**********************************************************************\
     *                                                                    *
     *                                                                    *
     *                 GETTERS, SETTERS, OBJECT OVERRIDES                 *
     *                                                                    *
     *                                                                    *
    \**********************************************************************/
    
    /**
     * Gets the id of the question.
     * @return the id of the question
     */
    public String getId () {
        return id;
    }
    
    /**
     * Gets the Course/Topic of tbe question.
     * @return 
     */
    public String getTopic () {
        return topic;
    }
    
    /**
     * Gets the content body of the question, with unevaluated JS parts.
     * @return the content of the question
     */
    public String getContent () {
        return content;
    }
    
    /**
     * The choices of the question
     * @return the ArrayList of choices 
     */
    public List<String> getChoices () {
        return choices;
    }
    
    /**
     * The tips of the question
     * @return the ArrayList of Tips
     */
    public List<String> getTips () {
        return tips;
    }
    
    /**
     * Gets the variation variables of the question.
     * @return a 2d-double array of variation data
     */
    public double[][] getVariation () {
        return variation;
    }
    
    /**
     * Gets the answers of the question
     * @return an array of answers
     */
    public int[] getAnswer () {
        return answer;
    }
    
    /**
     * Sets the id of the question. a 32-bit hex-encoded number. 
     * @param id the id to set the id to
     * @throws IllegalArgumentException if the id is invalid
     */
    private void setId (String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException("ID cannot be null or empty string");
        }
        this.id = id;
    }
    
    /**
     * Set the Topic from a String.
     * @param topic the raw String to determine a topic from
     */
    private void setTopic(String topic) {
        if (topic == null || topic.equals("")) {
            throw new IllegalArgumentException("Topic cannot be null or empty string");
        }
        this.topic = topic;
    }

    /**
     * Sets the content text for the question
     * @param content the text for the question
     */
    private void setContent(String content) {
        if (content == null || content.equals("")) {
            throw new IllegalArgumentException("Content cannot be null or empty string");
        }
        this.content = content;
    }
    
    /**
     * Sets the choices to given ArrayList<String>
     * @param choices the choices to set
     */
    private void setChoices (List<String> choices) {
        if (choices == null) {
            throw new IllegalArgumentException("choices cannot be null");
        }
        this.choices = choices;
    }
    
    /**
     * Sets the tips to given ArrayList<String>
     * @param tips the tips to set
     */
    private void setTips (List<String> tips) {
        if (tips == null) {
            throw new IllegalArgumentException("tips cannot be null");
        }
        this.tips = tips;
    }
    
    /**
     * Sets the variation of the Question.
     * @param d the Array of variation from a number, to a number  and by an 
     * interval.
     */
    private void setVariation (double[][] d) {
        if (d == null) {
            throw new IllegalArgumentException("variation cannot be null");
        }
        this.variation = d;
    }
    
    /**
     * Sets the answer of the question
     */
    private void setAnswer (int[] answer) {
        if (answer == null) {
            throw new IllegalArgumentException("Answer is null");
        }
        if (answer.length < 1) {
            throw new IllegalArgumentException("Question has no answer");
        }
        this.answer = answer;
    }

    /**
     * Creates a String representation of a Question.
     * @return a String representation of a Question.
     */
    @Override
    public String toString () {
        String s = "Question[";
        
        if(id != null) s += "\n\tid=" + id + ",";
        if(topic != null) s += "\n\ttopic=" + topic + ",";
        if(content != null) s += "\n\tcontent=" + content + ","; 
        if(!choices.isEmpty()) s += "\n\tchoices=" + Arrays.toString(choices.toArray()) + ",";
        if(!tips.isEmpty()) s += "\n\ttips=" + Arrays.toString(tips.toArray()) + ",";
        if(variation.length > 0) s += "\n\tvariation=" + Arrays.toString(variation) + ","; 
        
        return s + "\n]";
    }
    
}
