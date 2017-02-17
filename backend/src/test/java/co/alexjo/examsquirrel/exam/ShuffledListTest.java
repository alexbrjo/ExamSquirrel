package co.alexjo.examsquirrel.exam;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests class ShuffledList
 * @author Alex Johnson
 */
public class ShuffledListTest {
    
    /**
     * Tests the constructor of ShuffledList
     */
    @Test
    public void testNewShuffledList() {
        
        ShuffledList<String> list = new ShuffledList<>();
        assertNotNull(list); // the list should have been constructed
        assertEquals(0, list.size()); // there should be no elements
    }

    /**
     * Tests method add of Shuffled List using Strings
     */
    @Test
    public void testAdd() {
        
        ShuffledList<String> list = new ShuffledList<>();
        list.add("cat");
        list.add("dog");
        list.add("pig");
        
        // order could be storted, this only checks that all 3 strings exist
        // in the list
        int cat = 0;
        int dog = 0;
        int pig = 0;
        for (String s : list) {
            switch (s) {
                case "cat":
                    cat++;
                    break;
                case "dog":
                    dog++;
                    break;
                case "pig":
                    pig++;
                    break;
            }
        }
        // that theres only one element of each String
        assertEquals("String added incorrectly", 1, cat);
        assertEquals("String added incorrectly", 1, dog);
        assertEquals("String added incorrectly", 1, pig);
        
    }
    
    /**
     * Tests method add with index of Shuffled List
     */
    @Test
    public void testAddWithIndex() {
        
        ShuffledList<String> list = new ShuffledList<>();
        list.add(0, "apple");
        list.add(1, "orange");
        list.add(2, "banana");
        
        // list should be in order
        int i = 0;
        for (String s : list) {
            switch (s) {
                case "apple":
                    assertEquals(0, i);
                    break;
                case "orange":
                    assertEquals(1, i);
                    break;
                case "banana":
                    assertEquals(2, i);
                    break;
            }
            i++;
        }
        
    }
    
    /**
     * Tests the order of Shuffled List
     */
    @Test
    public void testOrder() {
        
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        ShuffledList<String> list = new ShuffledList<>(4);
        // adds all days to list
        for (String s : days) {
            list.add(s);
        }
        
        boolean allEquals = true; // start assuming its true
        // tests the order of the list
        int i = 0;
        for (String s : list) {
            allEquals = allEquals && (s.equals(days[i]));
            i++;
        }
        
        // the probability of the random order being in order is 1/(7!)
        // which is ~0.01%
        assertFalse(allEquals);
        
    }
    
    /**
     * Tests the method shuffle of ShuffledList
     */
    @Test
    public void testShuffle() {
        
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
                "Friday", "Saturday"};
        ShuffledList<String> list = new ShuffledList<>(4);
        // adds all days to list
        for (String s : days) {
            list.add(s);
        }
        
        boolean allEquals = true; // start assuming its true
        // tests the order of the list
        int i = 0;
        for (String s : list) {
            allEquals = allEquals && (s.equals(days[i]));
            i++;
        }
        
        // the probability of the random order being in order is 1/7!
        // which is ~0.01%
        assertFalse(allEquals);
        
    }
    
}
