package ua.com.blaclion.abstract_classes;

import ua.com.blaclion.classes.*;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Parent class for all fishes in the ocean
*/
public abstract class Fish extends OceanShape {
    private Rectangle2D oceanShape;
    private java.util.List<DrawFish> drawFishes;
    private Ocean ocean;
    private ExecutorService executor;
    private List<MoveFish> moveFishes;
    private MainFrame mainFrame;
    private int pointYDelta;
    private int lifeDays;
    private int newFishDays;
    private SetCoordinate setCoordinate;
    private static int amountOfFishes;
    private static int amountOfPredators;

    /**
     * Implement method for particular type of fish
     */
    public abstract void swim();

    /**
     * Common method for all types of fishes
     */
    protected void makeNewFish() {
        Point2D newFishPoint = new Point2D.Double(this.getXPoint() + this.getWidth(), this.getYPoint());

        if (newFishDays == 0 && !getContainer().isPointNear(newFishPoint, this)) {
            Fish newFish = new FishFactory().getNewFish(getClass());

            //Increase counter for Gold fishes
            if (newFish.getClass() == GoldFish.class) {
                Fish.increaseAmountOfFishes();
            }

            //Increase counter for predators
            if (newFish.getClass() == PredatorFish.class) {
                Fish.increaseAmountOfPredators();
            }

            //Check if coordinate of new fish inside the ocean
            //If not then correct coordinate
            setCoordinate = new SetCoordinate(getMainFrame(), getOceanSize(), newFish, getPointYDelta());
            while (newFish.getXPoint() + newFish.getWidth() >= getOceanSize().getMaxX()) {
                setCoordinate.correctXCoordinate();
            }

            //Set up all needing containers, objects and corrected coordinate
            newFish.setXPoint((int) newFishPoint.getX());
            newFish.setYPoint((int) newFishPoint.getY());
            newFish.setDrawFishes(getDrawFishes());
            newFish.setContainer(getContainer());
            newFish.setExecutor(getExecutor());
            newFish.setOceanSize(getOceanSize());
            newFish.setMoveFishes(getMoveFishes());

            //Add new fish to common fish's container
            getDrawFishes().add(new DrawFish(newFish));

            //Add new fish to common container of all objects in the ocean
            getContainer().setPoint(newFish.getExemplar(), new Point2D.Double(newFish.getXPoint(), newFish.getYPoint()));
            getContainer().setObject(newFish.getExemplar(), newFish);

            //Make new fish move
            MoveFish moveFish = new MoveFish(newFish, getOcean());
            getMoveFishes().add(moveFish);
            getExecutor().execute(moveFish);

            //Set upp for current fish new makeFish timer
            newFishDays = new Random(System.currentTimeMillis()).nextInt(100);
        }

        if (newFishDays > 0) {
            newFishCounter();
        }
    }

    /**
     * Method for setting ocean size
     */
    public void setOceanSize(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }

    /**
     * Method for setting container of drawing fishes
     */
    public void setDrawFishes(List<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    /**
     * Method for setting common panel where the fishes will be drawing
     */
    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    /**
     * Method for setting common pool of threads for all fishes
     */
    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    /**
     * Method for setting common pool of threads for all fishes
     */
    public void setMoveFishes(List<MoveFish> moveFishes) {
        this.moveFishes = moveFishes;
    }

    /**
     * Method for getting ocean size to positioning fish inside it
     */
    public Rectangle2D getOceanSize() {
        return oceanShape;
    }


    public List<DrawFish> getDrawFishes() {
        return drawFishes;
    }

    //Get ocean for repaint particular fish in it
    public Ocean getOcean() {
        return ocean;
    }

    //Get common thread pool for all fishes
    public ExecutorService getExecutor() {
        return executor;
    }

    //Get container with moving fishes
    public List<MoveFish> getMoveFishes() {
        return moveFishes;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public int getPointYDelta() {
        return pointYDelta;
    }

    public void setPointYDelta(int pointYDelta) {
        this.pointYDelta = pointYDelta;
    }

    public void setLifeDays(int lifeDays) {
        this.lifeDays = lifeDays;
    }

    public void setNewFishDays(int newFishDays) {
        this.newFishDays = newFishDays;
    }

    public int getLifeDays() {
        return lifeDays;
    }

    protected void deathCounter(){
        lifeDays--;
    }

    protected void newFishCounter() {
        newFishDays--;
    }

    protected void checkOceanBounds(int xDirection, int yDirection) {
        if (getOceanSize().getMaxX() <= getXPoint() + xDirection + getWidth()
                || getOceanSize().getMaxX() - getOceanSize().getWidth() >= getXPoint() + xDirection) {
            setXPoint(getXPoint() - xDirection);
        } else {
            setXPoint(getXPoint() + xDirection);
        }

        if (getOceanSize().getMaxY() <= getYPoint() + yDirection + getHeight()
                || getOceanSize().getMaxY() - getOceanSize().getHeight() >= getYPoint() + yDirection) {
            setYPoint(getYPoint() - yDirection);
        } else {
            setYPoint(getYPoint() + yDirection);
        }
    }

    protected void holdNextStep() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Integer getAmountOfFishes() {
        return amountOfFishes;
    }

    public static Integer getAmountOfPredators() {
        return amountOfPredators;
    }

    public static void increaseAmountOfFishes() {
        amountOfFishes++;
    }

    public static void increaseAmountOfPredators() {
        amountOfPredators++;
    }

    public static void decreaseAmountOfFishes() {
        amountOfFishes--;
    }

    public static void decreaseAmountOfPredators() {
        amountOfPredators--;
    }
}
