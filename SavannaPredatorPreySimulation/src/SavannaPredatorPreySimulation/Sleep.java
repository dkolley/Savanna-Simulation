package SavannaPredatorPreySimulation;

/**
 * Sleep class that checks whether the animal should be asleep
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Sleep
{
    // Variables to calculate whether the animal should be asleep.
    private int sleep;
    private int wake;
    private int hour;

    /**
     * Creates an instance of sleep.
     * @param sleep The time when the animal falls asleep.
     * @param wake The time when the animal wakes up.
     * @param hour The current time.
     */
    public Sleep(int sleep, int wake, int hour)
    {
        this.sleep = sleep;
        this.wake = wake;
        this.hour = hour;
    }

    /**
     * Returns true if the animal should be sleeping.
     * @returns true if the animal should be sleeping.
     */
    protected boolean checkSleep()
    {
        if(sleep < wake) {
            if(sleep <= hour && wake > hour) {
                return true;
            } else {
                return false;
            }
        } else {
            if(sleep <= hour || wake > hour) {
                return true;
            } else {
                return false;
            }
        }
    }
}
