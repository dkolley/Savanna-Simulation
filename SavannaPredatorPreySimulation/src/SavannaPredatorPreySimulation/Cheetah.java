package SavannaPredatorPreySimulation;

/**
 * The cheetah's class.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Cheetah extends Predator
{
    // Characteristics shared by all cheetahes (class variables).
    // The age at which a cheetah can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a cheetah can live.
    private static final int MAX_AGE = 120;
    // Maximum food level a cheetah can have
    private static final int MAX_FOOD_VALUE = 14;
    // The likelihood of a cheetah breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // Name of the animal in a String.
    private static final String NAME = "Cheetah";
    // When the animal falls asleep.
    private static final int SLEEP = 17;
    // How much the animal sleeps.
    private static final int SLEEP_AMOUNT = 14;
    // Preys that cheetahs eat.
    private static final String[] preys = {"Gazelle", "Antelope"};
    
    /**
     * Create a cheetah. A cheetah can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the cheetah will have random age and hunger level.
     * @param savanna The savanna currently occupied.
     * @param location The location within the savanna.
     */
    public Cheetah(boolean randomAge, SavannaAnimal savanna, Location location)
    {
        super(randomAge, savanna, location, MAX_AGE, MAX_FOOD_VALUE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, NAME, SLEEP, SLEEP_AMOUNT, preys);
    }
    
    /**
     * Return a new animal of the same species.
     * @param savanna The savanna the animal is in.
     * @param location The location of the newborn animal.
     * @return The new animal that was born.
     */
    protected Animal makeNewAnimal(SavannaAnimal savanna, Location location)
    {
        return new Cheetah(false, savanna, location);
    }
}