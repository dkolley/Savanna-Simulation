package SavannaPredatorPreySimulation;

import java.util.Random;

/**
 * Represent a rectangular grid of savanna positions.
 * Each position is able to store a single animal.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public abstract class Savanna
{
    // A random number generator for providing random locations.
    protected static final Random rand = Randomizer.getRandom();
    
    // The depth and width of the savanna.
    protected int depth, width;
    
    /**
     * Represent a savanna of the given dimensions.
     * @param depth The depth of the savanna.
     * @param width The width of the savanna.
     */
    public Savanna(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
    }
    
    /**
     * Empty the savanna.
     */
    abstract public void clear();
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    abstract public void clear(Location location);
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    abstract public void place(Object animal, int row, int col);
    
    /**
     * Place an animal at the given location.
     * If there is already an animal at the location it will
     * be lost.
     * @param animal The animal to be placed.
     * @param location Where to place the animal.
     */
    abstract public void place(Object animal, Location location);
    
    /**
     * Return the animal at the given location, if any.
     * @param location Where in the savanna.
     * @return The animal at the given location, or null if there is none.
     */
    abstract public Object getObjectAt(Location location);
    
    /**
     * Return the animal at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The animal at the given location, or null if there is none.
     */
    abstract public Object getObjectAt(int row, int col);

    /**
     * Return the depth of the savanna.
     * @return The depth of the savanna.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * Return the width of the savanna.
     * @return The width of the savanna.
     */
    public int getWidth()
    {
        return width;
    }
}
