package SavannaPredatorPreySimulation;


/**
 * Rain extends Weather and is a type of weather that can occur
 * while the simulation is running. Rain helps plants grow faster.
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Rain extends Weather
{
    private static final String NAME = "Rain";
    private static final double PLANT_GROWTH_CHANGE = 2.5;
    private static final double PROBABILITY = 0.42;
    private static final int WEATHER_DURATION = 6;

    /**
     * Constructor for objects of class Rain
     */
    public Rain()
    {
        super(NAME, PLANT_GROWTH_CHANGE, PROBABILITY, WEATHER_DURATION);
    }
}
