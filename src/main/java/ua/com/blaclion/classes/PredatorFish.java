package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * Class that create predator fish
 */
public class PredatorFish extends Fish {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;
    private DrawFish thisDrawFish;
    private MoveFish thisMoveFish;
    private int hungryDeath;

    public PredatorFish() {
        setWidth(40);
        setHeight(15);

        fishColor = Color.RED;

        setLifeDays(new Random(System.currentTimeMillis()).nextInt(300));

        setNewFishDays(new Random(System.currentTimeMillis()).nextInt(100));

        logger.info("Fish created " + getExemplar());

        setToHungryDeath();
    }

    @Override
    public void swim() {
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
        hungryDeathCounter();
        newFishCounter();

        makeNewFish();
        killFish();

        Point2D fishNextPoint = new Point2D.Double(getXPoint() + xDirection, getYPoint() + yDirection);
        Point2D fishCurrentPoint = new Point2D.Double(getXPoint(), getYPoint());

        OceanShape maybeGoldFish = getContainer().isPredatorNearGoldFish(fishCurrentPoint, fishNextPoint, this);
        if (maybeGoldFish != null) {
            GoldFish goldFish = (GoldFish) maybeGoldFish;
            goldFish.fishAte();
            setToHungryDeath();
            logger.info("Fish " + goldFish.getExemplar() + " has eaten");
        } else if (getContainer().isFuturePosNearSomeObject(fishCurrentPoint, fishNextPoint, this)) {
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

        PredatorFish fish = (PredatorFish) obj;

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
     * Method that set up steps counter to death moment
     */
    private void setToHungryDeath() {
        hungryDeath = 30;
    }

    /**
     * Method that decrease counter to death moment
     */
    private void hungryDeathCounter() {
        hungryDeath--;
    }

    /**
     * Delete this fish from all common containers and stop is thread
     */
    private void killFish() {
        if (getLifeDays() == 0 || hungryDeath == 0) {
            getDrawFishes().remove(thisDrawFish);
            getMoveFishes().remove(thisMoveFish);
            thisMoveFish.kill();
            decreaseAmountOfPredators();
            logger.info("Exemplar " + this.getExemplar() + " is dead");
        }
    }
}
