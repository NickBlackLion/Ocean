package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

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

    public void fishAte() {
        kill();
    }

    private void killFish() {
        if (getLifeDays() == 0) {
            kill();
        }
    }

    private void kill() {
        thisMoveFish.kill();
        getContainer().remove(getExemplar());
        getDrawFishes().remove(thisDrawFish);
        getMoveFishes().remove(thisMoveFish);
        decreaseAmountOfFishes();
        logger.info("Exemplar " + this.getExemplar() + " is dead");
    }
}
