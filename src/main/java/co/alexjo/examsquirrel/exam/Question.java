package co.alexjo.examsquirrel.exam;

import java.util.ArrayList;
import java.util.Arrays;

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
public class Question {
    
    /** The id of the question */
    private String id;
    /** The topic of the question */
    private String topic;
    
    /** The text of the question */
    private String content;
    /** The choices of the question, index 0 is the answer */
    private ArrayList<String> choices;
    /** Tips for solving the question */
    private ArrayList<String> tips;
    
    /** The lowest number to vary the question from */
    private double[][] variation;
    
    /**
     * Creates a 
     * @param id The unique identification string of the question
     * @param topic The topic of the question
     * @param content The text of the question
     * @param choices The choices of the question, index 0 is the answer
     * @param tips Tips for solving the question
     * @param variation The lowest number to vary the question from
     */
    public Question (String id, String topic, String content, ArrayList<String> choices, 
            ArrayList<String> tips, double[][] variation) {
        super();
        
        setId(id);
        setTopic(topic);
        setContent(content);
        setChoices(choices);
        setTips(tips);
        setVariation(variation);
    }
    
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
    public ArrayList<String> getChoices () {
        return choices;
    }
    
    /**
     * The tips of the question
     * @return the ArrayList of Tips
     */
    public ArrayList<String> getTips () {
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
     * Sets the id of the question. a 32-bit hex-encoded number. 
     * @param id the id to set the id to
     * @throws IllegalArgumentException if the id is invalid
     */
    private void setId (String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    /**
     * Set the Topic from a String.
     * @param topic the raw String to determine a topic from
     */
    private void setTopic(String topic) {
        if (topic == null || topic.equals("")) {
            throw new IllegalArgumentException();
        }
        this.topic = topic;
    }

    /**
     * Sets the content text for the question
     * @param content the text for the question
     */
    private void setContent(String content) {
        if (content == null || content.equals("")) {
            throw new IllegalArgumentException();
        }
        this.content = content;
    }
    
    /**
     * Sets the choices to given ArrayList<String>
     * @param choices the choices to set
     */
    private void setChoices (ArrayList<String> choices) {
        if (choices == null) {
            throw new IllegalArgumentException();
        }
        this.choices = choices;
    }
    
    /**
     * Sets the tips to given ArrayList<String>
     * @param tips the tips to set
     */
    private void setTips (ArrayList<String> tips) {
        if (tips == null) {
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
        }
        this.variation = d;
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
