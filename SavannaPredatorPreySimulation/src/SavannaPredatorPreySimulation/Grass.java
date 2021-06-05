package SavannaPredatorPreySimulation;

/**
 * An abstract class representing shared characteristics of preys.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Grass extends Plant
{
    // The value that when x reaches the grass regrows.
    private static final int REGROWTH_VALUE = 4;
    
    /**
     * Create a new grass.
     * @param savanna The savanna where the plants are.
     * @param location The location of the plant
     */
    public Grass(SavannaPlant savanna, Location location)
    {
        super(savanna, location);
    }

    /**
     * This is what the plant does every step of the simulation.
     * @param weatherChangeRatio What the weather does to the plant's death counter.
     * @param isWeather True if there is a weather event.
     */
    
    public void grow(double weatherChangeRatio, boolean isWeather) {
        if(!alive) {
            if(isWeather) {
                setX(weatherChangeRatio);
                if(getX() >= REGROWTH_VALUE + 1) {
                    alive = true;
                    setXZero();
                }
            } else {
                incrementX();
                if(getX() >= REGROWTH_VALUE + 1) {
                    alive = true;
                    setXZero();
                }
            }
        }
    }
}