package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Class that create gold fish
 */
public class GoldFish extends Fish {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;
    private DrawFish thisDrawFish;
    private MoveFish thisMoveFish;

    public GoldFish() {
        setWidth(20);
        setHeight(10);

        fishColor = new Color(255,215,0);

        setLifeDays(new Random(System.currentTimeMillis()).nextInt(300));

        setNewFishDays(new Random(System.currentTimeMillis()).nextInt(100));

        logger.info("Fish created " + getExemplar());
    }


    @Override
    public void swim(){
        int xDirection = new Random(System.currentTimeMillis()).nextInt(20) - 10;
        int yDirection = new Random(System.currentTimeMillis()).nextInt(20) - 10;

        //Find this fish in common container of drew fishes
        if(thisDrawFish == null){
            for (DrawFish drawFish: getDrawFishes()) {
                if (drawFish.getFish().equals(this)) {
                    thisDrawFish = drawFish;
                }
            }
        }

        //Find this fish in common container of moving fishes
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

        if (getContainer().isFuturePosNearSomeObject(fishCurrentPoint, fishNextPoint, this)){
            xDirection = 0;
            yDirection = 0;
            logger.info("It's existed fish near exemplar " + getExemplar());
        }

        checkOceanBounds(xDirection, yDirection);

        Point2D currentFishPoint = new Point2D.Double(getXPoint(), getYPoint());
        getContainer().setPoint(this.getExemplar(), currentFishPoint);
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

    /**
     *Signalizes from outside if this fish is eaten
     *kill this fish
     */
    public void fishAte() {
        kill();
    }

    /**
     *Kill fish if days are left
     */
    private void killFish() {
        if (getLifeDays() == 0) {
            kill();
        }
    }

    /**
     * Delete this fish from all common containers and stop is thread
     */
    private void kill() {
        thisMoveFish.stop();
        getContainer().remove(getExemplar());
        getDrawFishes().remove(thisDrawFish);
        getMoveFishes().remove(thisMoveFish);
        decreaseAmountOfFishes();
        logger.info("Exemplar " + this.getExemplar() + " is dead");
    }
}
