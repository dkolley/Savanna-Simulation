package SavannaPredatorPreySimulation;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

/**
 * A predator-prey simulator, based on a rectangular savanna
 * containing animals and plants. With events happening frequently.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 360;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 240;
    // The probability that a lion will be created in any given grid position.
    private static final double LION_CREATION_PROBABILITY = 0.04;
    // The probability that a cheetah will be created in any given grid position.
    private static final double CHEETAH_CREATION_PROBABILITY = 0.04;
    // The probability that a buffalo will be created in any given grid position.
    private static final double BUFFALO_CREATION_PROBABILITY = 0.08;
    // The probability that a zebra will be created in any given grid position.
    private static final double ZEBRA_CREATION_PROBABILITY = 0.08;
    // The probability that a gazelle will be created in any given grid position.
    private static final double GAZELLE_CREATION_PROBABILITY = 0.08;
    // The probability that a giraffe will be created in any given grid position.
    private static final double GIRAFFE_CREATION_PROBABILITY = 0.08;
    // The probability that an antelope will be created in any given grid position.
    private static final double ANTELOPE_CREATION_PROBABILITY = 0.08;
    // The frequency of the event happenning.
    private static final int WEATHER_PER_STEP = 20;
    private static final int DISEASE_PER_STEP = 69;

    // List of animals in the savanna.
    private List<Animal> animals;
    // The current state of the savanna.
    private SavannaAnimal savannaAnimal;
    // List of animals in the savanna.
    private List<Plant> plants;
    // The current state of the savanna.
    private SavannaPlant savannaPlant; 
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // The current time of the simulation.
    private Time time;
    // The fields for the weather event.
    private Weather rain;
    private Weather drought;
    private Weather currentWeather;
    private boolean isWeather;
    private int weatherStartStep;
    // The fields for the disease event.
    private Disease currentDisease;
    private Disease blackDeath;
    private int diseaseStartStep;
    private boolean isDisease;
    // A randomizer for the simulator.
    private Random rand = Randomizer.getRandom();
    
    /**
     * Construct a simulation savanna with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Create a simulation savanna with the given size.
     * @param depth Depth of the savanna. Must be greater than zero.
     * @param width Width of the savanna. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        
        time = new Time();
        
        animals = new ArrayList<>();
        savannaAnimal = new SavannaAnimal(depth, width);
        
        plants = new ArrayList<>();
        savannaPlant = new SavannaPlant(depth, width);
        
        rain = new Rain();
        drought = new Drought();
        
        isWeather = false;
        
        blackDeath = new BlackDeath();
        
        isDisease = false;

        // Create a view of the state of each location in the savanna.
        view = new SimulatorView(depth, width);
        view.setColor(Buffalo.class, Color.MAGENTA);
        view.setColor(Gazelle.class, Color.RED);
        view.setColor(Zebra.class, Color.GREEN);
        view.setColor(Cheetah.class, Color.CYAN);
        view.setColor(Lion.class, Color.BLUE);
        view.setColor(Antelope.class, Color.YELLOW);
        
        // Setup a valid starting point.
        reset();
    }
    
    /**
     * Run the simulation from its current state for a reasonably longer period.
     */
    public void runLongSimulation()
    {
        simulate(300);
    }
    
    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(savannaAnimal); step++) {
            simulateOneStep();
            // delay(60);   // uncomment this to run more slowly
        }
    }
    
    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole savanna updating the state of each
     * animal and plant.
     * Invoke the makeWeather() and makeDisease() methods, which generate an event.
     */
    public void simulateOneStep()
    {
        step++;
        time.calculateTime(step);
        
        makeWeather();
        if(isWeather) {
            currentWeather = getCurrentWeather();
        }
        
        makeDisease();
        if(isDisease) {
            currentDisease = getCurrentDisease();
        }
        
        for(Iterator<Plant> it = plants.iterator(); it.hasNext(); ) {
            Plant plant = it.next();
            if(isWeather) {
                plant.grow(currentWeather.getGrowthChange(), isWeather);
            } else {
                plant.grow(1, isWeather);
            }
        }
        
        // Provide space for newborn animals.
        List<Animal> newAnimals = new ArrayList<>();        
        // Let all animals act.
        for(Iterator<Animal> it = animals.iterator(); it.hasNext(); ) {
            Animal animal = it.next();
            Sleep sleep = new Sleep(animal.getSleep(), animal.getWake(), time.getHour());
            boolean isAsleep = sleep.checkSleep();
            if(isDisease) {
                animal.act(newAnimals, isAsleep, isDisease, currentDisease.getDiseaseLethality(), currentDisease.getInfectionRate());
            } else {
                animal.act(newAnimals, isAsleep, isDisease, 1, 1);
            }
            if(!animal.isAlive()) {
                it.remove();
            }
        }
               
        // Add the newly born animals to the main lists.
        animals.addAll(newAnimals);

        showStatus();
    }
        
    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        animals.clear();
        populateAnimals();
        populatePlants();
        time.resetTime();
        isWeather = false;
        isDisease = false;
        
        
        // Show the starting state in the view.
        showStatus();
    }
    
    /**
     * Show the state of the savanna in the GUI.
     */
    private void showStatus()
    {
        if(isWeather && isDisease) {
            view.showStatus(step, time.getDay(), time.getHour(), savannaAnimal, currentWeather.getName(), isWeather, currentDisease.getName(), isDisease);
        } else if(isWeather && !isDisease) {
            view.showStatus(step, time.getDay(), time.getHour(), savannaAnimal, currentWeather.getName(), isWeather, "", isDisease);
        } else if(!isWeather && isDisease) {
            view.showStatus(step, time.getDay(), time.getHour(), savannaAnimal, "", isWeather, currentDisease.getName(), isDisease);
        } else {
            view.showStatus(step, time.getDay(), time.getHour(), savannaAnimal, "", isWeather, "", isDisease);
        }
    }
    
    /**
     * Randomly populate the savanna with animals.
     */
    private void populateAnimals()
    {
        savannaAnimal.clear();
        for(int row = 0; row < savannaAnimal.getDepth(); row++) {
            for(int col = 0; col < savannaAnimal.getWidth(); col++) {
                if(rand.nextDouble() <= LION_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Lion lion = new Lion(false, savannaAnimal, location);
                    animals.add(lion);
                } else if(rand.nextDouble() <= CHEETAH_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Cheetah cheetah = new Cheetah(false, savannaAnimal, location);
                    animals.add(cheetah);
                } else if(rand.nextDouble() <= BUFFALO_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Buffalo buffalo = new Buffalo(false, savannaAnimal, location, savannaPlant);
                    animals.add(buffalo);
                } else if(rand.nextDouble() <= ZEBRA_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Zebra zebra = new Zebra(false, savannaAnimal, location, savannaPlant);
                    animals.add(zebra);
                } else if(rand.nextDouble() <= GAZELLE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Gazelle gazelle = new Gazelle(false, savannaAnimal, location, savannaPlant);
                    animals.add(gazelle);
                } else if(rand.nextDouble() <= ANTELOPE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Antelope antelope = new Antelope(false, savannaAnimal, location, savannaPlant);
                    animals.add(antelope);
                }
                // else leave the location empty.
            }
        }
    }
    
    /**
     * Populate every location of the savanna with a plant.
     */
    private void populatePlants()
    {
        savannaPlant.clear();
        for(int row = 0; row < savannaAnimal.getDepth(); row++) {
            for(int col = 0; col < savannaAnimal.getWidth(); col++) {
                Location location = new Location(row, col);
                Grass grass = new Grass(savannaPlant, location);
                plants.add(grass);
            }
        }
    }
    
    /**
     * Generate weather event.
     */
    private void makeWeather()
    {
        if(step % WEATHER_PER_STEP == 0) {
            weatherStartStep = step;
            if(rand.nextDouble() <= rain.getProbability()) {
                isWeather = true;
                currentWeather = rain;
            } else if(rand.nextDouble() <= drought.getProbability()) {
                isWeather = true;
                currentWeather = drought;
            }
        }
        if(isWeather) {
            if(weatherStartStep + currentWeather.getDuration() <= step) {
                isWeather = false;
            }
        }
    }
    
    /**
     * Return the current weather if a weather event is active.
     * @return The current weather or null if there is no weather event right now.
     */
    private Weather getCurrentWeather()
    {
        if(currentWeather.getName().equals(rain.getName())) {
            return rain;
        } else if(currentWeather.getName().equals(drought.getName())) {
            return drought;
        }
        return null;
    }
    
    /**
     * Generate disease event.
     */
    private void makeDisease()
    {
        if(step % DISEASE_PER_STEP == 0) {
            diseaseStartStep = step;
            if(rand.nextDouble() <= blackDeath.getProbability()) {
                isDisease = true;
                currentDisease = blackDeath;
            }
        }
        if(isDisease) {
            if(diseaseStartStep + currentDisease.getDuration() <= step) {
                isDisease = false;
            }
        }
    }
    
    /**
     * Return the current disease if a disease event is active.
     * @return The current disease or null if there is no disease event right now.
     */
    private Disease getCurrentDisease()
    {
        if(currentDisease.getName().equals(blackDeath.getName())) {
            return blackDeath;
        }
        return null;
    }
    
    /**
     * Pause for a given time.
     * @param millisec The time to pause for, in milliseconds.
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    /**
     * Return the number of steps of the simulator.
     * @return The current step number of the simulator.
     */
    public int getStep()
    {
        return step;
    }
}