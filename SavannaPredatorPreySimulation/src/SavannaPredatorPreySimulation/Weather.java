package SavannaPredatorPreySimulation;


/**
 * Weather is an event that changes frequently during the simulation
 * The weather at the time has a direct impact on plant growth 
 * And in turn has an impact on the population of plant eaters 
 * And hence predators meaning weather affects all the animals in the simulation
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Weather
{
    //name of the type of weather
    private String name;
    //a value used that determines how fast a plant will grow
    private double plantGrowthChange;
    //a probability that a certain type of weather will be simulated
    private double probability;
    //the time in steps of how long that particular simulated weather will last for
    private int weatherDuration;

    /**
     * Constructor for objects of class Weather
     * @param name The name of the type of weather
     * @param plantGrowthChange How quick a plant grows depending on weather simulated
     * @param probability The probability of what type of weather occurs
     * @param weatherDuration The duration of how long that weather will last for in the simulation
     */
    public Weather(String name, double plantGrowthChange, double probability, int weatherDuration)
    {
        this.name = name;
        this.plantGrowthChange = plantGrowthChange;
        this.probability = probability;
        this.weatherDuration = weatherDuration;
    }
    
    /**
     * Return the name of a type of weather.
     * @return The name of type of weather.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the growth of a plant associated with the type of weather.
     * @return The growth change of the plants.
     */
    public double getGrowthChange()
    {
        return plantGrowthChange;
    }
    
    /**
     * Return the probability that a certain type of weather occurs
     * @return The probability that a certain type of weather occurs
     */
    public double getProbability()
    {
        return probability;
    }
    
    /**
     * Return the duration of the weather
     * @return The duration of the weather
     */
    public int getDuration()
    {
        return weatherDuration;
    }
}
