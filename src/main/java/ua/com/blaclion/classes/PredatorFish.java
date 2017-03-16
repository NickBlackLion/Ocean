package ua.com.blaclion.classes;

import javafx.util.Pair;
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
    private int hungryDeath;

    public PredatorFish() {
        setWidth(40);
        setHeight(15);

        fishColor = Color.RED;

        setLifeDays(new Random(System.currentTimeMillis()).nextInt(500));

        setNewFishDays(new Random(System.currentTimeMillis()).nextInt(200));

        logger.info("Fish created " + getExemplar());

        setToHungryDeath();
    }

    @Override
    public void swim() {
        int xDirection = (int)(Math.random()*20 - 10);
        int yDirection = (int)(Math.random()*20 - 10);

        //Find this fish in common container of drew fishes
        if(thisDrawFish == null){
            for (DrawFish drawFish: getDrawFishes()) {
                if (drawFish.getFish().equals(this)) {
                    thisDrawFish = drawFish;
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

        OceanShape shape = getContainer().isFuturePosNearSomeObject(fishCurrentPoint, fishNextPoint, this);
        if (shape != null && shape.getClass() != GoldFish.class) {
            xDirection = 0;
            yDirection = 0;
            logger.info("It's existed fish near exemplar " + getExemplar());
        } else {
            Pair<Point2D, OceanShape> pair = getContainer().makeNewPredatorPath(fishCurrentPoint, this);
            if (pair != null) {
                logger.info("Exemplar " + getExemplar() + " exemplar2 " + pair.getValue().getExemplar());
                Pair<Integer, Integer> directionPair = CheckDirection.direction(fishCurrentPoint, pair.getKey(),
                        getWidth(), getHeight(),
                        xDirection, yDirection);
                if (directionPair != null) {
                    xDirection = directionPair.getKey();
                    yDirection = directionPair.getValue();
                }
                /*
                    GoldFish goldFish = (GoldFish) pair.getValue();
                    goldFish.fishAte();
                    setToHungryDeath();
                    logger.info("Exemplar " + goldFish.getExemplar() + " ate by " + getExemplar());
                */
            }
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
     * Delete this fish from all common containers and stop is thread
     */
    private void killFish() {
        if (getLifeDays() == 0 || hungryDeath == 0) {
            kill();
            getDrawFishes().remove(thisDrawFish);
            decreaseAmountOfPredators();
        }
    }

    /**
     * Method that set up steps counter to death moment
     */
    private void setToHungryDeath() {
        hungryDeath = 100;
    }

    /**
     * Method that decrease counter to death moment
     */
    private void hungryDeathCounter() {
        hungryDeath--;
    }
}
