package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GoldFish extends Fish {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;
    private DrawFish thisDrawFish;
    private MoveFish thisMoveFish;
    private int lifeDays;
    private int newFishDays;
    private SetCoordinate setCoordinate;

    public GoldFish() {
        setWidth(20);
        setHeight(10);

        fishColor = new Color(255,215,0);

        lifeDays = new Random(System.currentTimeMillis()).nextInt(300);

        newFishDays = new Random(System.currentTimeMillis()).nextInt(100);

        logger.info("Fish created " + getExemplar());
    }

    @Override
    public void swim(){
        int xDirection = (int)(Math.random()*20 - 10);
        int yDirection = (int)(Math.random()*20 - 10);

        if(thisDrawFish == null){
            for (DrawFish drawFish: getDrawFishes()) {
                if (drawFish.getFish().equals(this)) {
                    thisDrawFish = drawFish;
                }
            }
        }

        if(thisMoveFish == null){
            for (MoveFish moveFish: getMoveFishes()) {
                if (moveFish.getFish().equals(this)) {
                    thisMoveFish = moveFish;
                }
            }
        }

        deathCounter();
        newFishCounter();

        makeNewFish();
        killFish();

        Point2D fishNextPoint = new Point2D.Double(getXPoint() + xDirection, getYPoint() + yDirection);
        Point2D fishCurrentPoint = new Point2D.Double(getXPoint(), getYPoint());

        if (getContainer().isPointNear(fishCurrentPoint, fishNextPoint, this)){
            xDirection = 0;
            yDirection = 0;
            logger.info("It's existed fish near exemplar " + getExemplar());

            holdNextStep();
        }

        checkOceanBounds(xDirection, yDirection);

        Point2D currentFishPoint = new Point2D.Double(getXPoint(), getYPoint());
        getContainer().setPoint(this.getExemplar(), currentFishPoint);

        holdNextStep();
    }

    @Override
    public void makeNewFish() {
        Point2D newFishPoint = new Point2D.Double(this.getXPoint() + this.getWidth(), this.getYPoint());

        if (newFishDays == 0 && !getContainer().isPointNear(newFishPoint, newFishPoint, this)) {
            Fish newFish = new FishFactory().getNewFish(GoldFish.class);
            setCoordinate = new SetCoordinate(getMainFrame(), getOceanShape(), newFish, getPointYDelta());
            while (getContainer().isPointNear(newFishPoint, newFishPoint, this)) {
                setCoordinate.correctXCoordinate();
                setCoordinate.correctYCoordinate();
            }
            newFish.setXPoint((int) newFishPoint.getX());
            newFish.setYPoint((int) newFishPoint.getY());
            newFish.setDrawFishes(getDrawFishes());
            newFish.setContainer(getContainer());
            newFish.setExecutor(getExecutor());
            newFish.setOceanShape(getOceanShape());
            newFish.setMoveFishes(getMoveFishes());

            getDrawFishes().add(new DrawFish(newFish));

            getContainer().setPoint(newFish.getExemplar(), new Point2D.Double(newFish.getXPoint(), newFish.getYPoint()));
            getContainer().setObject(newFish.getExemplar(), newFish);
            MoveFish moveFish = new MoveFish(newFish, getOcean());
            getMoveFishes().add(moveFish);
            getExecutor().execute(moveFish);

            newFishDays = new Random(System.currentTimeMillis()).nextInt(100);
        } else {
            newFishCounter();
        }
    }

    public Color getColor() {
        return fishColor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() != getClass()){
            return false;
        }

        GoldFish fish = (GoldFish) obj;

        if (fish.getXPoint() != getXPoint()){
            return false;
        }

        if (fish.getYPoint() != getYPoint()){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Xpoint = " + this.getXPoint() + " Ypoint = " + this.getYPoint();
    }

    private void deathCounter(){
        lifeDays--;
    }

    private void newFishCounter() {
        newFishDays--;
    }

    private void killFish() {
        if (lifeDays == 0) {
            getDrawFishes().remove(thisDrawFish);
            getMoveFishes().remove(thisMoveFish);
            thisMoveFish.kill();
            logger.info("Exemplar " + this.getExemplar() + " is dead");
        }
    }

    private void holdNextStep() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkOceanBounds(int xDirection, int yDirection) {
        if (getOceanShape().getMaxX() <= getXPoint() + xDirection + getWidth()
                || getOceanShape().getMaxX() - getOceanShape().getWidth() >= getXPoint() + xDirection) {
            setXPoint(getXPoint() - xDirection);
        } else {
            setXPoint(getXPoint() + xDirection);
        }

        if (getOceanShape().getMaxY() <= getYPoint() + yDirection + getHeight()
                || getOceanShape().getMaxY() - getOceanShape().getHeight() >= getYPoint() + yDirection) {
            setYPoint(getYPoint() - yDirection);
        } else {
            setYPoint(getYPoint() + yDirection);
        }
    }
}
