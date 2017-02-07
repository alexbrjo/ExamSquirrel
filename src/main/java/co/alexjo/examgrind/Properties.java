package co.alexjo.examgrind;

import java.util.Map;

/**
 * The mapping for the JSON properties for a Course directory.
 * @author Alex Johnson
 */
public class Properties {
    
    /** The course id */
    private String course;
    /** The name of the course */
    private String name;
    /** The course material */
    private Map<String, String[]> material;

    /**
     * Gets the Course Id.
     * @return the course
     */
    public String getCourse() {
        return course;
    }

    /**
     * Sets the course id.
     * @param course the course to set
     * @throws IllegalArgumentException if course is null
     */
    public void setCourse(String course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        this.course = course;
    }

    /**
     * Gets the course name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the course name.
     * @param name the name to set
     * @throws IllegalArgumentException if name is null
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Gets the course Material
     * @return the material
     */
    public Map<String, String[]> getMaterial() {
        return material;
    }

    /**
     * Sets the Course material.
     * @param material the material to set
     * @throws IllegalArgumentException if material is null
     */
    public void setMaterial(Map<String, String[]> material) {
        if (name == null) {
            throw new IllegalArgumentException("Material cannot be null");
        }
        this.material = material;
    }
    
}
