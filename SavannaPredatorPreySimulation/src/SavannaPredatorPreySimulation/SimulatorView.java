package SavannaPredatorPreySimulation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A graphical view of the simulation grid.
 * The view displays a colored rectangle for each location 
 * representing its contents. It uses a default background color.
 * Colors for each type of species can be defined using the
 * setColor method.
 * 
 * @author Dennis Kolley
 * @version 2021.03.01
 */
public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;
    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;
    //The abbreviation will be one of two values: "AM" or "PM"
    String abbreviation = "";
    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private final String DAY_PREFIX = "Day: ";
    private final String WEATHER_PREFIX = "Current weather: ";
    private final String HOUR_POSTFIX = ":00";
    private final String DISEASE_PREFIX = "Current disease: ";
    private JLabel stepLabel, population, timeLabel, eventLabel;
    private savannaView savannaView;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, Color> colors;
    // A statistics object computing and storing simulation information
    private SavannaStats stats;

    /**
     * Create a view of the given width and height.
     * Set the title, create labels that display information related
     * To the current status of the simulation
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    public SimulatorView(int height, int width)
    {
        stats = new SavannaStats();
        colors = new LinkedHashMap<>();
        setTitle("Savanna simulation");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        timeLabel = new JLabel(DAY_PREFIX, JLabel.CENTER);
        eventLabel = new JLabel("", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        setLocation(100, 50);
        savannaView = new savannaView(height, width);
        Container contents = getContentPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        infoPane.add(stepLabel, BorderLayout.WEST);
        infoPane.add(timeLabel, BorderLayout.CENTER);
        infoPane.add(eventLabel, BorderLayout.EAST);
        contents.add(infoPane, BorderLayout.NORTH);
        contents.add(savannaView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
    
    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
     * Display a short information label at the top of the window.
     * @param text Displays the current time of the simulation
     */
    public void setTimeText(String text)
    {
        timeLabel.setText(text);
    }
    
    /**
     * Display a short information label at the top of the window.
     * @param text Displays the current event of the simulation
     */
    public void setEventText(String text)
    {
        eventLabel.setText(text);
    }

    /**
     * @param animalClass The class of the animal in question
     * @return The color to be used for a given class of animal.
     */
    private Color getColor(Class animalClass)
    {
        Color col = colors.get(animalClass);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Show the current status of the savanna.
     * @param step Which iteration step it is.
     * @param day Will store either "Day" or "Night" depending on the hour value of time is.
     * @param hour The hour that represents the hour of the time of day (unit of time).
     * @param savanna The savanna whose status is to be displayed.
     * @param weatherName Stores the current weather of the sim ==> At the moment can be either "Rain" or "Drought".
     * @param isWeather Whether or not weather has been simulated. 
     * @param diseaseName What type of disease it is that an animal can have.
     * @param isDisease Lets simulation know whether disease event is currently ongoing.
     */
    public void showStatus(int step, int day, int hour, Savanna savanna, String weatherName, boolean isWeather, String diseaseName, boolean isDisease)
    {
        if(!isVisible()) {
            setVisible(true);
        }
        
        //Change the abbreviation depending on the current hour value of the simulation
        if(hour >= 12) {
            abbreviation = "PM";
        } else {
            abbreviation = "AM";
        }
        
        //every time status is updated (done every step)
        //We update the step label which is +1
        //We update the time tabel which is hour+1 and new abbreviation used depending on hour
        stepLabel.setText(STEP_PREFIX + step);
        timeLabel.setText(DAY_PREFIX + day + ", " + hour + HOUR_POSTFIX + abbreviation);
        
        
        if(isWeather && isDisease) {
            eventLabel.setText(WEATHER_PREFIX + weatherName + ", " + DISEASE_PREFIX + diseaseName);
            //If weather event and disease event occur at same time
            //We must display both to user therefore concatenate variables associated with weather and disease 
        } else if(isWeather && !isDisease) {
            //Only display weather label if disease event false and weather event currently occuring
            eventLabel.setText(WEATHER_PREFIX + weatherName);
        } else if(!isWeather && isDisease) {
            //Only display disease label if weather event false and disease event currently occuring
            eventLabel.setText(DISEASE_PREFIX + diseaseName);
        } else {
            //When no event occurs display nothing to the user
            eventLabel.setText("");
        }
        
        stats.reset();
        savannaView.preparePaint();

        for(int row = 0; row < savanna.getDepth(); row++) {
            for(int col = 0; col < savanna.getWidth(); col++) {
                Object animal = savanna.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    savannaView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    savannaView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(savanna));
        savannaView.repaint();
    }

    /**
     * Determine whether the simulation should continue to run.
     * @param savanna The savanna of our simulation that all the animals interact and behave on
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Savanna savanna)
    {
        return stats.isViable(savanna);
    }
    
    /**
     * Provide a graphical view of a rectangular savanna. This is 
     * a nested class (a class defined inside a class) which
     * defines a custom component for the user interface. This
     * component displays the savanna.
     */
    private class savannaView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 4;
        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image savannaImage;

        /**
         * Create a new savannaView component.
         * @param height The height to create forthe grid of the simulation
         * @param width The width to create for the grid of the simulation
         */
        public savannaView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        /**
         * Prepare for a new round of painting. Since the component
         * may be resized, compute the scaling factor again.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                savannaImage = savannaView.createImage(size.width, size.height);
                g = savannaImage.getGraphics();
                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Paint on grid location on this savanna in a given color.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * The savanna view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g)
        {
            if(savannaImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(savannaImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(savannaImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}
