package ua.com.blaclion.abstract_classes;

import ua.com.blaclion.classes.*;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Parent class for all fishes in the ocean
*/
public abstract class Fish extends OceanShape {
    private Rectangle2D oceanShape;
    private SafetyList<DrawFish> drawFishes;
    private Ocean ocean;
    private SafetyList<MoveFish> moveFishes;
    private MainFrame mainFrame;
    private int pointYDelta;
    private int lifeDays;
    private int newFishDays;
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

        if (newFishDays == 0 && !getContainer().isSomeObjectNear(newFishPoint, this)) {
            Fish newFish = FishFactory.getNewFish(getClass());

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
            if (newFishPoint.getX() + this.getWidth() >= getOceanSize().getMaxX()) {
                newFishPoint.setLocation(this.getXPoint() - this.getWidth(), this.getYPoint());
            }

            //Set up all needing containers, objects and corrected coordinate
            newFish.setXPoint((int) newFishPoint.getX());
            newFish.setYPoint((int) newFishPoint.getY());
            newFish.setDrawFishes(getDrawFishes());
            newFish.setContainer(getContainer());
            newFish.setOceanSize(getOceanSize());
            newFish.setMoveFishes(getMoveFishes());
            newFish.setOcean(getOcean());

            //Add new fish to common fish's container
            getDrawFishes().add(new DrawFish(newFish));

            //Add new fish to common container of all objects in the ocean
            getContainer().setPoint(newFish.getExemplar(), new Point2D.Double(newFish.getXPoint(), newFish.getYPoint()));
            getContainer().setObject(newFish.getExemplar(), newFish);

            //Make new fish move
            MoveFish moveFish = new MoveFish(newFish, getOcean());
            getMoveFishes().add(moveFish);

            //Set upp for current fish new makeFish timer
            newFishDays = new Random(System.currentTimeMillis()).nextInt(100);
        }

        if (newFishDays > 0) {
            newFishCounter();
        }
    }

    public void setOceanSize(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }

    public void setDrawFishes(SafetyList<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    public void setMoveFishes(SafetyList<MoveFish> moveFishes) {
        this.moveFishes = moveFishes;
    }

    public Rectangle2D getOceanSize() {
        return oceanShape;
    }

    public SafetyList<DrawFish> getDrawFishes() {
        return drawFishes;
    }

    public Ocean getOcean() {
        return ocean;
    }

    public SafetyList<MoveFish> getMoveFishes() {
        return moveFishes;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Method for getting common delta that shows where ocean is started
     */
    public int getPointYDelta() {
        return pointYDelta;
    }

    /**
     * Method for setting common delta that shows where ocean is started
     */
    public void setPointYDelta(int pointYDelta) {
        this.pointYDelta = pointYDelta;
    }

    protected void setLifeDays(int lifeDays) {
        this.lifeDays = lifeDays;
    }

    /**
     * Method for setting days before current fish will make new fish
     */
    protected void setNewFishDays(int newFishDays) {
        this.newFishDays = newFishDays;
    }

    public int getLifeDays() {
        return lifeDays;
    }

    /**
     * Method that takes fish closer to death :(
     */
    protected void deathCounter(){
        lifeDays--;
    }

    /**
     * Method that takes fish closer to bring new life :)
     */
    protected void newFishCounter() {
        newFishDays--;
    }

    /**
     * Method that check if future direction of fish is in bounds of the ocean
     * if not changes direction
     */
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
