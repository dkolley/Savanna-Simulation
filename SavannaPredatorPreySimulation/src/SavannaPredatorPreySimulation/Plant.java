package SavannaPredatorPreySimulation;

/**
 * An abstract class representing shared characteristics of preys.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public abstract class Plant extends Actor
{
    // The location of the plant.
    private Location location;
    // The savanna of the plant.
    private SavannaPlant savanna;
    // Counter for how long the plant has been dead.
    private double x;
    /**
     * Create a new plant.
     * @param savanna The savanna where the plants are.
     * @param location The location of the plant
     */
    public Plant(SavannaPlant savanna, Location location)
    {
        super();
        this.savanna = savanna;
        setLocation(location);
        x = 0;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Return the savanna of the plants.
     * @return The savanna where the plants are.
     */
    protected SavannaPlant getSavanna()
    {
        return savanna;
    }
    
    /**
     * Set the location of the animal.
     * @param location Where to place the animal.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            savanna.clear(location);
        }
        location = newLocation;
        savanna.place(this, newLocation);
    }
    
    /**
     * This is what the plant does every step of the simulation.
     * @param weatherChangeRatio What the weather does to the plant's death counter.
     * @param isWeather True if there is a weather event.
     */
    abstract public void grow(double weatherChangeRatio, boolean isWeather);
    
    /**
     * Make the plant dead and set the death counter to 0.
     */
    public void setDead()
    {
        alive = false;
        x = 0;
    }
    
    /**
     * If there is a weather event it changes the value of x differently.
     * @param weatherChangeRatio The value that gets added to x.
     */
    protected void setX(double weatherChangeRatio)
    {
        x = x + (weatherChangeRatio);
    }
    
    /**
     * Increments the value of x.
     */
    protected void incrementX()
    {
        x++;
    }
    
    /**
     * Returns the value of x.
     * @return The value of x.
     */
    protected double getX()
    {
        return x;
    }
    
    /**
     * Set the value of x to 0.
     */
    protected void setXZero()
    {
        x = 0;
    }
}
