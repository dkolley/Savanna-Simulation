package SavannaPredatorPreySimulation;

import java.util.List;
import java.util.Iterator;

/**
 * An abstract class representing shared characteristics of preys.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public abstract class Prey extends Animal
{
    // The savanna where the plants are.
    private SavannaPlant savannaPlant;
    
    /**
     * Create a new prey at location in savanna.
     * 
     * @param randomAge True if the animal borns with a random age.
     * @param savanna The savanna currently occupied.
     * @param location The location within the savanna.
     * @param maxAge The animal's maximum age.
     * @param maxFoodValue The animal's maximum food level.
     * @param breedingAge The age at which the animal can start giving birth.
     * @param maxLitterSize The maximum number of kids an animal can have when giving birth.
     * @param breedingProbability The probability that an animal will give birth when meeting an opposite gender.
     * @param name The name of the species of the animal.
     * @param sleep The time at when the animal falls asleep.
     * @param sleepAmount The number of hours the animal sleeps.
     * @param savannaPlant The savanna where the plants are.
     */
    public Prey(boolean randomAge, SavannaAnimal savanna, Location location, int maxAge, int maxFoodValue, int breedingAge, int maxLitterSize, double breedingProbability, String name, int sleep, int sleepAmount, SavannaPlant savannaPlant)
    {
        super(randomAge, savanna, location, maxAge, maxFoodValue, breedingAge, maxLitterSize, breedingProbability, name, sleep, sleepAmount);
        this.savannaPlant = savannaPlant;
    }
    
    /**
     * Return the savanna of the plants.
     * @return The savanna where the plants are.
     */
    private SavannaPlant getSavannaPlant()
    {
        return savannaPlant;
    }
    
    /**
     * Look for preys adjacent to the current location.
     * Only the first live plant is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        SavannaPlant savanna = getSavannaPlant();
        List<Location> adjacent = savanna.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object object = savanna.getObjectAt(where);
            if(object instanceof Plant) {
                Plant plant  = (Plant) object;
                if(plant.isAlive()) {
                    plant.setDead();
                    setFoodValue(super.getMaxFoodValue());
                    return where;
                }
            }
        }
        return null;
    }
    
    /**
     * Return a new animal of the same species.
     * @param savanna The savanna the animal is in.
     * @param location The location of the newborn animal.
     * @param savannaPlant The savanna where the plants are.
     * @return The new animal that was born.
     */
    abstract protected Animal makeNewAnimal(SavannaAnimal savanna, Location location, SavannaPlant savannaPlant);
    
    /**
     * Give birth to (a) new animal(s).
     * @param newAnimals The list of newborn animal(s).
     */
    protected void giveBirth(List<Animal> newAnimals)
    {
        // New cheetahs are born into adjacent locations.
        // Get a list of adjacent free locations.
        SavannaAnimal savanna = getSavanna();
        SavannaPlant savannaPlant = getSavannaPlant();
        List<Location> free = savanna.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Animal young = makeNewAnimal(savanna, loc, savannaPlant);
            newAnimals.add(young);
        }
    }
}