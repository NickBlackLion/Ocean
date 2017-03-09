package ua.com.blaclion.abstract_classes;

import ua.com.blaclion.classes.*;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

//Parent class for all fishes in the ocean
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

    public abstract void swim();

    protected void makeNewFish() {
        Point2D newFishPoint = new Point2D.Double(this.getXPoint() + this.getWidth(), this.getYPoint());

        if (newFishDays == 0 && !getContainer().isPointNear(newFishPoint, this)) {
            Fish newFish = new FishFactory().getNewFish(getClass());

            setCoordinate = new SetCoordinate(getMainFrame(), getOceanSize(), newFish, getPointYDelta());
            while (newFish.getXPoint() + newFish.getWidth() >= getOceanSize().getMaxX()) {
                setCoordinate.correctXCoordinate();
            }

            newFish.setXPoint((int) newFishPoint.getX());
            newFish.setYPoint((int) newFishPoint.getY());
            newFish.setDrawFishes(getDrawFishes());
            newFish.setContainer(getContainer());
            newFish.setExecutor(getExecutor());
            newFish.setOceanSize(getOceanSize());
            newFish.setMoveFishes(getMoveFishes());

            getDrawFishes().add(new DrawFish(newFish));

            getContainer().setPoint(newFish.getExemplar(), new Point2D.Double(newFish.getXPoint(), newFish.getYPoint()));
            getContainer().setObject(newFish.getExemplar(), newFish);
            MoveFish moveFish = new MoveFish(newFish, getOcean());
            getMoveFishes().add(moveFish);
            getExecutor().execute(moveFish);

            newFishDays = new Random(System.currentTimeMillis()).nextInt(100);
        }

        if (newFishDays > 0) {
            newFishCounter();
        }
    }

    public void setOceanSize(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }

    public void setDrawFishes(List<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setMoveFishes(List<MoveFish> moveFishes) {
        this.moveFishes = moveFishes;
    }

    //Get ocean size for positioning fish inside it
    public Rectangle2D getOceanSize() {
        return oceanShape;
    }

    //Get container with prepared fishes for drawing
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
}
