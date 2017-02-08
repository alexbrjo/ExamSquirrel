package co.alexjo.examsquirrel.exam;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * The raw Question object. Has unevaluated.
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
     * Constructs a new Question
     */
    public Question () {
        super();
        
        variation = new double[1][3];
        choices = new ArrayList<>();
        tips = new ArrayList<>();
    }
    
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
        this();
        setId(id);
        setTopic(topic);
        setContent(content);
        setChoices(choices);
        setTips(tips);
        setVariation(variation);
    }
    
    public String getId () {
        return id;
    }
    
    public String getTopic () {
        return topic;
    }
    
    public String getContent () {
        return content;
    }
    
    public ArrayList<String> getChoices () {
        return choices;
    }
    
    public ArrayList<String> getTips () {
        return tips;
    }
    
    public double[][] getVariation () {
        return variation;
    }
    
    public void setId (String id) {
        if (id == null || id.equals("")) {
            throw new IllegalArgumentException();
        }
        this.id = id;
    }
    
    /**
     * Set the Topic from a String.
     * @param topic the raw String to determine a topic from
     */
    public void setTopic(String topic) {
        if (topic == null || topic.equals("")) {
            throw new IllegalArgumentException();
        }
        this.topic = topic;
    }

    /**
     * Sets the content text for the question
     * @param content the text for the question
     */
    public void setContent(String content) {
        if (content == null || content.equals("")) {
            throw new IllegalArgumentException();
        }
        this.content = content;
    }
    
    /**
     * Sets the choices to given ArrayList<String>
     * @param choices the choices to set
     */
    public void setChoices (ArrayList<String> choices) {
        if (choices == null) {
            throw new IllegalArgumentException();
        }
        this.choices = choices;
    }
    
    /**
     * Adds a choice to the choice ArrayList
     * @param choice the choice to add
     */
    public void addChoice (String choice) {
        if (choice == null) {
            throw new IllegalArgumentException();
        }
        choices.add(choice);
    }
    
    /**
     * Sets the tips to given ArrayList<String>
     * @param tips the tips to set
     */
    public void setTips (ArrayList<String> tips) {
        if (tips == null) {
            throw new IllegalArgumentException();
        }
        this.tips = tips;
    }
    
    /**
     * Adds a Question tip to the tips ArrayList
     * @param tip the tip to add
     */
    public void addTip (String tip) {
        if (tip == null || tip.endsWith(tip)) {
            throw new IllegalArgumentException();
        }
        tips.add(tip);
    }
    
    /**
     * Sets the variation of the Question.
     * @param d the Array of variation from a number, to a number  and by an 
     * interval.
     */
    public void setVariation (double[][] d) {
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
