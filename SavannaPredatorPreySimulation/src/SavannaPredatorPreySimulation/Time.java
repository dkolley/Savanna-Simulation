package SavannaPredatorPreySimulation;

/**
 * Time class that keeps track of the time in the simulator.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Time
{
    // Variables to keep track of time.
    private int hour = 0;
    private int day = 1;
    // The number of steps an hour is.
    private static final int STEP_PER_HOUR = 2;
    // The number of steps a day is.
    private static final int STEP_PER_DAY = STEP_PER_HOUR * 24;

    /**
     * Creates an instance of Time.
     */
    public Time()
    {
        hour = 0;
        day = 1;
    }

    /**
     * Calculates the time in the simulator.
     * @param steps The step at which the simulation is at.
     */
    public void calculateTime(int steps)
    {
        hour = (steps / STEP_PER_HOUR) % 24;
        day = (steps / STEP_PER_DAY) + 1;
    }
    
    /**
     * Returns the hour.
     * @return The hour.
     */
    public int getHour()
    {
        return hour;
    }
    
    /**
     * Returns the day.
     * @return The day.
     */
    public int getDay()
    {
        return day;
    }
    
    /**
     * Resets the time.
     */
    public void resetTime()
    {
        hour = 0;
        day = 1;
    }
}

