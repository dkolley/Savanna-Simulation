package SavannaPredatorPreySimulation;

/**
 * The buffalo's class.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Buffalo extends Prey
{
    // Characteristics shared by all buffalos (class variables).
    // The age at which a buffalo can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a buffalo can live.
    private static final int MAX_AGE = 180;
    // The maximum food level of buffalo.
    private static final int MAX_FOOD_VALUE = 30;
    // The likelihood of a buffalo breeding.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 3;
    // Name of the animal in a String.
    private static final String NAME = "Buffalo";
    // When the animal falls asleep.
    private static final int SLEEP = 10;
    // How much the animal sleeps.
    private static final int SLEEP_AMOUNT = 4;

    /**
     * Create a new buffalo. A buffalo may be created with age
     * zero (a new born) or with a random age.
     * @param randomAge If true, the antelope will have a random age.
     * @param savanna The savanna currently occupied.
     * @param location The location within the savanna.
     * @param savannaPlant The savanna where the plants are.
     */
    public Buffalo(boolean randomAge, SavannaAnimal savanna, Location location, SavannaPlant savannaPlant)
    {
        super(randomAge, savanna, location, MAX_AGE, MAX_FOOD_VALUE, BREEDING_AGE, MAX_LITTER_SIZE, BREEDING_PROBABILITY, NAME, SLEEP, SLEEP_AMOUNT, savannaPlant);
    }
    
    /**
     * Return a new animal of the same species.
     * @param savanna The savanna the animal is in.
     * @param location The location of the newborn animal.
     * @param savannaPlant The savanna where the plants are.
     * @return The new animal that was born.
     */
    protected Animal makeNewAnimal(SavannaAnimal savanna, Location location, SavannaPlant savannaPlant)
    {
        return new Buffalo(false, savanna, location, savannaPlant);
    }
}

