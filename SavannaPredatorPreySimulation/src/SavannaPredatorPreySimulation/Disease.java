package SavannaPredatorPreySimulation;

import java.util.Random;
/**
 * Disease is an event that occasionally infects each and every animal in the simulation
 * The disease can spread from "sick" animals to "healthy" ones
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Disease
{
    //name of the type of disease
    private String name;
    //how much the animal gets older by if they have this disease
    //larger the lethality, the quicker the animal reaches its max age and dies
    private int diseaseLethality;
    //a probability value  used during disease event to decide which type of disease an animal will have
    private double probability;
    //Used alongside randomly generated value and compared to determine whether the animal will get "infected" or not
    private double infectionRate;
    //How many steps the disease lasts for and affects the animals 
    private int duration;
    
    /**
     * Disease constructor
     * Create a new disease when disease event occurs
     * @param name The name of the type of disease
     * @param diseaseLethality The lethality of the disease.
     * @param probability The probability that a certain disease occurs.
     * @param infectionRate The infection rate of the disease.
     * @param duration The duration of the disease.
     */
    public Disease(String name, int diseaseLethality, double probability, double infectionRate, int duration)
    {
        this.name = name;
        this.diseaseLethality = diseaseLethality;
        this.probability = probability;
        this.infectionRate = infectionRate;
        this.duration = duration;
    }
    
    /**
     * Return the name of a particular disease.
     * @return The name of the disease.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Return the value of lethality associated with a particular disease.
     * @return The lethality of the disease.
     */
    public int getDiseaseLethality()
    {
        return diseaseLethality;
    }
    
    /**
     * Return the probability of that particular disease happening
     * @return The probability associated with a disease happening
     */
    public double getProbability()
    {
        return probability;
    }
    
    /**
     * Return the infection rate of that particular disease 
     * @return The infection rate of that particular disease 
     */
    public double getInfectionRate()
    {
        return infectionRate;
    }
    
    /**
     * Return the duration of a disease
     * @return The duration of a disease
     */
    public int getDuration()
    {
        return duration;
    }
}