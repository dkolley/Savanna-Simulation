package SavannaPredatorPreySimulation;


/**
 * Drought extends Weather and is a type of weather that can occur
 * while the simulation is running. Droughts slow down the growth of plants.
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Drought extends Weather
{
    private static final String NAME = "Drought";
    private static final double PLANT_GROWTH_CHANGE = 0.69;
    private static final double PROBABILITY = 0.69;
    private static final int WEATHER_DURATION = 12;
    
    /**
     * Constructor for objects of class Drought
     */
    public Drought()
    {
        super(NAME, PLANT_GROWTH_CHANGE, PROBABILITY, WEATHER_DURATION);
    }
}
