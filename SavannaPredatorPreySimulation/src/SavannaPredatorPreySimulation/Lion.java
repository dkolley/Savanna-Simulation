package SavannaPredatorPreySimulation;

/**
 * The cheetah's class.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Lion extends Predator
{
    // Characteristics shared by all liones (class variables).
    // The age at which a lion can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a lion can live.
    private static final int MAX_AGE = 180;
    // Maximum food level a lion can have
    private static final int MAX_FOOD_VALUE = 12;
    // The likelihood of a lion breeding.
    private static final double BREEDING_PROBABILITY = 0.14;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 6;
    // Name of the animal in a String.
    private static final String NAME = "Lion";
    // When the animal falls asleep.
    private static final int SLEEP = 8;
    // How much the animal sleeps.
    private static final int SLEEP_AMOUNT = 10;
    // Preys that lions eat.
    private static final String[] preys = {"Buffalo", "Zebra", "Antelope"};
    
    /**
     * Create a lion. A lion can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the lion will have random age and hunger level.
     * @param savanna The savanna currently occupied.
     * @param location The location within the savanna.
     */
    public Lion(boolean randomAge, SavannaAnimal savanna, Location location)
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
        return new Lion(false, savanna, location);
    }
}