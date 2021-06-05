package SavannaPredatorPreySimulation;

import java.util.List;
import java.util.Random;
import java.util.Iterator;

/**
 * An abstract class representing shared characteristics of animals.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public abstract class Animal extends Actor
{
    // The animal's savanna.
    private SavannaAnimal savanna;
    // The animal's location.
    private Location location;
    // The animal's age.
    private int age;
    // The animal's maximum age.
    private int maxAge;
    // The animal's food level, which is increased by eating.
    private int foodValue;
    // The animal's maximum food level.
    private int maxFoodValue;
    // The age an animal can start breeding at.
    private int breedingAge;
    // The maximum number of offspring an animal can have when breeding.
    private int maxLitterSize;
    // The probability that 2 animal of the same type breed when the opposite genders meet.
    private double breedingProbability;
    // The gender of the animal
    private boolean isFemale;
    // The name of the animal.
    private String name;
    // Time that the animal falls asleep.
    private int sleep;
    // How much time the animal falls asleep for.
    private int sleepAmount;
    // When the animal wakes up.
    private int wake;
    // A random generator for the animal
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in savanna.
     * The animal can be created with random age.
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
     */
    public Animal(boolean randomAge, SavannaAnimal savanna, Location location, int maxAge, int maxFoodValue, int breedingAge, int maxLitterSize, double breedingProbability, String name, int sleep, int sleepAmount)
    {
        super();
        this.savanna = savanna;
        setLocation(location);
        this.maxAge = maxAge;
        this.maxFoodValue = maxFoodValue;
        this.breedingAge = breedingAge;
        this.maxLitterSize = maxLitterSize;
        this.breedingProbability = breedingProbability;
        this.name = name;
        this.sleepAmount = sleepAmount;
        this.sleep = sleep;
        wake = (sleep + sleepAmount) % 24;
        createGender();

        if(randomAge) {
            age = rand.nextInt(maxAge);
            foodValue = rand.nextInt(maxFoodValue);
        } else {
            age = 0;
            foodValue = maxFoodValue;
        }
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     * @param isAsleep True if the animal is asleep.
     * @param isDisease True if the animal is sick.
     * @param lethality The lethality of the disease.
     * @param infectionRate The infection rate of the disease.
     */
    public void act(List<Animal> newAnimals, boolean isAsleep, boolean isDisease, int lethality, double infectionRate)
    {
        if(!isAsleep) {
            incrementHunger();
            incrementAge(isDisease, lethality, infectionRate);
            if(isAlive()) {
                if(meet()) {
                    giveBirth(newAnimals);  
                }          
                // Move towards a source of food if found.
                Location newLocation = findFood();
                if(newLocation == null) {
                    // No food found - try to move to a free location.
                    newLocation = getSavanna().freeAdjacentLocation(getLocation());
                }
                // See if it was possible to move.
                if(newLocation != null) {
                    setLocation(newLocation);
                } else {
                    // Overcrowding.
                    setDead();
                }
            }
        }
    }
    
    /**
     * Look for preys adjacent to the current location.
     * Only the first live prey is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    abstract protected Location findFood();
    
    /**
     * Give birth to (a) new animal(s).
     * @param newAnimals The list of newborn animal(s).
     */
    abstract protected void giveBirth(List<Animal> newAnimals);
    
    /**
     * Create a gender for the animal.
     * 50/50 that it's either a male or female.
     */
    private void createGender()
    {
        int random = rand.nextInt(2);
        if(random == 0) {
            isFemale = true;
        } else {
            isFemale = false;
        }
    }
    
    /**
     * Return the name of the species of the animal.
     * @return The name of the species of the animal.
     */
    protected String getName()
    {
        return name;
    }
    
    /**
     * Return the animal's max food level.
     * @return The animal's max food level.
     */
    protected int getMaxFoodValue()
    {
        return maxFoodValue;
    }
    
    /**
     * Return the gender of the animal.
     * @return true if the animal is female.
     */
    private boolean getIsFemale()
    {
        return isFemale;
    }
    
    /**
     * Set the food value of the animal.
     * @param n The value that we want to set the food value to.
     */
    protected void setFoodValue(int n)
    {
        foodValue = n;
    }
    
    /**
     * Return the max age of the animal.
     * @return The max age of the animal.
     */
    protected int getMaxAge()
    {
        return maxAge;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the savanna.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            savanna.clear(location);
            location = null;
            savanna = null;
        }
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
     * Return the savanna of the animal.
     * @return The savanna that the animal is in.
     */
    protected SavannaAnimal getSavanna()
    {
        return savanna;
    }
    
    /**
     * Increase the age.
     * This could result in the animal's death.
     * @param isDisease True if the animal is sick.
     * @param lethality The lethality of the disease.
     * @param infectionRate The infection rate of the disease.
     */
    protected void incrementAge(boolean isDisease, int lethality, double infectionRate)
    {
        if (isDisease && rand.nextDouble() <= infectionRate) {
            age = age + lethality;
        } else {
            age++;
        }

        if(age > maxAge) {
            setDead();
        }
    }
    
    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodValue--;
        if(foodValue <= 0) {
            setDead();
        }
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
     * Return true when two animal of the same species and opposite gender meet at an adjacent location.
     * @return true when two animal of the same species and opposite gender meet at an adjacent location.
     */
    protected boolean meet()
    {
        SavannaAnimal savanna = getSavanna();
        List<Location> adjacent = savanna.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object object = savanna.getObjectAt(where);
            if(object instanceof Animal) {
                Animal animal = (Animal) object;
                if (animal.getName().equals(name) && !(animal.getIsFemale() == isFemale)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= breedingProbability) {
            births = rand.nextInt(maxLitterSize) + 1;
        }
        return births;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     * @return true if the animal can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= breedingAge;
    }
    
    /**
     * Return when the animal falls asleep.
     * @return The time when the animal falls asleep.
     */
    public int getSleep()
    {
        return sleep;
    }
    
    /**
     * Return when the animal wakes up.
     * @return The time when the animal wakes up.
     */
    public int getWake()
    {
        return wake;
    }
}