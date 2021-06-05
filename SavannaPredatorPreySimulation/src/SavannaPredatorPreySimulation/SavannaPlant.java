package SavannaPredatorPreySimulation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represent a rectangular grid of savanna positions.
 * Each position is able to store a single animal.
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class SavannaPlant extends Savanna
{
    // Storage for the plants.
    private Object[][] savanna;

    /**
     * Constructor for objects of class SavannaPlant.
     * @param depth The depth of the savanna.
     * @param width The width of the savanna.
     */
    public SavannaPlant(int depth, int width)
    {
        super(depth, width);
        savanna = new Object[depth][width];
    }
    
    /**
     * Empty the savanna.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                savanna[row][col] = null;
            }
        }
    }
    
    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location)
    {
        savanna[location.getRow()][location.getCol()] = null;
    }
    
    /**
     * Place an plant at the given location.
     * If there is already an plant at the location it will
     * be lost.
     * @param plant The plant to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Object plant, int row, int col)
    {
        place(plant, new Location(row, col));
    }
    
    /**
     * Place an plant at the given location.
     * If there is already an plant at the location it will
     * be lost.
     * @param plant The plant to be placed.
     * @param location Where to place the plant.
     */
    public void place(Object plant, Location location)
    {
        savanna[location.getRow()][location.getCol()] = plant;
    }
    
    /**
     * Return the plant at the given location, if any.
     * @param location Where in the savanna.
     * @return The plant at the given location, or null if there is none.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Return the plant at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The plant at the given location, or null if there is none.
     */
    public Object getObjectAt(int row, int col)
    {
        return savanna[row][col];
    }
    
    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location)
    {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if(location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for(int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if(nextRow >= 0 && nextRow < depth) {
                    for(int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }
            
            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }
}