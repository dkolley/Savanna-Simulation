package SavannaPredatorPreySimulation;

/**
 * An abstract class storing the shared fields of the actors of the simulation.
 *
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public abstract class Actor
{
    // Whether the actor is alive or not.
    protected boolean alive;

    /**
     * Constructor of class Actor.
     */
    public Actor()
    {
        alive = true;
    }
    
    /**
     * Check whether the actor is alive or not.
     * @return true if the actor is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }
}