package SavannaPredatorPreySimulation;

/**
 * BlackDeath is a subclass of disease class 
 * and is a type of disease that could affect the animal during simulation
 *
 *@author Dennis Kolley
 *@version 2021.03.01
 */
public class BlackDeath extends Disease
{
    private static final String NAME = "Black Death";
    private static final int DISEASE_LETHALITY = 20; 
    private static final double PROBABILITY = 0.69;
    private static final double INFECTION_RATE = 0.69;
    private static final int DURATION = 20;
    
    /**
     * Constructor for objects of class BlackDeath
     */
    public BlackDeath()
    {
        super(NAME, DISEASE_LETHALITY, PROBABILITY, INFECTION_RATE, DURATION);
    }
}

