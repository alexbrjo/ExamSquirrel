package co.alexjo.examsquirrel.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * A list that shuffles its contents on addition. Wraps an ArrayList and several
 * of its methods. ShuffledList can quickly collect objects and iterate 
 * through them in a random order. ShuffledList is not intended to store or
 * handle a large number of objects.
 * 
 * The user is not supposed to know the order of the elements in a ShuffledList 
 * so there is no get for index method. The correct way to access the shuffled 
 * elements is with a for each loop. Since there is no indices, to remove an 
 * object you have to have a reference to the object.
 * 
 * @author Alex Johnson
 * @param <T> the type of object stored in ShuffledList
 */
public class ShuffledList<T> implements Iterable<T> {
    
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
        return new ShuffledIterator();
    }
    
    /**
     * Iterates through the shuffled list using a specific pattern
     */
    private class ShuffledIterator implements Iterator<T> {
        /** The cursor of the iterator */
        private int cursor;
        
        /**
         * Constructs a new QuestionIterator.
         */
        public ShuffledIterator () {
            if (shuffleOnNewIteration) shuffle();
            cursor = 0;
        }
        
        /**
         * If the iterator has a next Question.
         * @return true if the iterator has a next element; otherwise false
         */
        @Override
        public boolean hasNext() {
            return cursor < list.size();
        }

        /**
         * Gets the next element in the list. Flips through the items.
         * @return the next element in the list
         */
        @Override
        public T next() {
            if (hasNext()) {
                T element = list.get(cursor);
                cursor++;
                return element;
            } else {
                throw new NoSuchElementException();
            }
        } 
    }
}
