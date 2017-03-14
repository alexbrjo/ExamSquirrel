package co.alexjo.examsquirrel.exam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/**
 * A list that shuffles its contents on addition. Wraps an ArrayList and several
 * of its methods. ShuffledList can quickly collect objects and iterate 
 * through them in a random order. ShuffledList is not intended to store or
 * handle a large number of objects.
 * 
 * @author Alex Johnson
 * @param <T> the type of object stored in ShuffledList
 */
public class ShuffledList<T> implements Iterable<T> { //, List<T> {
    
    /** The ArrayList of elements */
    private ArrayList<T> list;
    /** The random number generator */
    private Random random;
    /** Whether to shuffle the list ever new time it's iterated through */
    private boolean shuffleOnNewIteration;
    /** The number of time the List has been shuffled */
    private int shuffled;
    
    /**
     * Constructs a new ShuffledList for random seed.
     */
    public ShuffledList () {
        this(Integer.MAX_VALUE);
    }
    
    /**
     * Constructs a new ShuffledList for given seed.
     * @param seed the seed to sort the items to
     */
    public ShuffledList (int seed) {
        if (seed == Integer.MAX_VALUE) {
            random = new Random();
        } else {
            random = new Random(seed);
        }
        // create ArrayList
        list = new ArrayList<>();
        // By default, don't shuffle ever new time the list is iterated through
        shuffleOnNewIteration = false;
    }
    
    /**
     * Shuffles the Array.
     */
    public void shuffle () {
        // shallow copy of all of the elements
        ArrayList<T> temp = (ArrayList<T>)list.clone();
        clear();
        for (T o : temp) {
            add(o);
        }
        shuffled++;
    }
    
    /**
     * Gets the number of times the list has been shuffled, excluding
     * shuffling on addition
     * @return the number of times shuffled
     */
    public int getTimesShuffled () {
        return shuffled;
    }
    
    /**
     * Adds an element of type T to ShuffledList at a random index
     * @param element the element to add
     */
    public void add (T element) {
        add((int)Math.round(random.nextDouble() * list.size()), element);
    }
    
    /**
     * Adds an element of type T to ShuffledList at a specific index
     * @param index the index to insert the element
     * @param element the object, of type T, to insert
     */
    public void add (int index, T element) {
        list.add(index, element);
    }
    
    /**
     * Adds an ArrayList of elements to the List
     * @param elements the objects, of type T, to insert
     */
    public void add (ArrayList<T> elements) {
        for (T element : elements) {
            add(element);
        }
    }
    
    /**
     * Gets the index of an object in the list
     * @return the integer index of the object in the list
     */
    public int indexOf (T o) {
        return list.indexOf(o);
    }
    
    /**
     * Removes an object from the List
     * @param element the object to remove
     */
    public void remove (T element) {
        list.remove(element);
    }
    
    /**
     * Gets the size of the List
     * @return the size of the list
     */
    public int size () {
        return list.size();
    }
    
    /**
     * Gets the size of the List
     * @return the size of the list
     */
    public boolean isEmpty () {
        return list.isEmpty();
    }
    
    /**
     * Clears the list.
     */
    public void clear() {
        list.clear();
    }
    
    /**
     * Gets the iterator for ShuffledList. Very basic implementation.
     * @return the ShuffledList iterator
     */
    @Override
    public Iterator<T> iterator() {
        // shuffle if enabled
        if (shuffleOnNewIteration)
            shuffle();
        
        // use list's iterator
        return list.iterator();
    }
}
