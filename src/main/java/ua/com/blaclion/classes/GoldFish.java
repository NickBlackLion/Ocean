package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoldFish extends Fish {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;
    private Rectangle2D oceanShape;
    private java.util.List<DrawFish> drawFishes;
    private Timer timer;
    private boolean deathTimerStart = false;
    private DrawFish thisFish;

    public GoldFish() {
        timer = new Timer();

        setWidth(20);
        setHeight(10);

        fishColor = new Color(255,215,0);

        timer = new Timer();
    }

    @Override
    public void swim(){
        int xDirection = (int)(Math.random()*20 - 10);
        int yDirection = (int)(Math.random()*20 - 10);

        if(!deathTimerStart){
            //deathTimer();
            deathTimerStart = true;
            for (DrawFish drawFish: drawFishes) {
                if (drawFish.getFish().equals(this)) {
                    thisFish = drawFish;
                }
            }
        }

        Point2D fishNextPoint = new Point2D.Double(getXPoint() + xDirection, getYPoint() + yDirection);
        Point2D fishCurrentPoint = new Point2D.Double(getXPoint(), getYPoint());

        if (getContainer().isPointNear(fishNextPoint, fishCurrentPoint, getWidth(), getHeight())){
            xDirection = 0;
            yDirection = 0;
            logger.info("It's existed fish near exemplar " + getExemplar());

            holdNextStep();
        }

        if (oceanShape.getMaxX() <= getXPoint() + xDirection + getWidth()
                    || oceanShape.getMaxX() - oceanShape.getWidth() >= getXPoint() + xDirection) {
            setXPoint(getXPoint() - xDirection);
        } else {
            setXPoint(getXPoint() + xDirection);
        }

        if (oceanShape.getMaxY() <= getYPoint() + yDirection + getHeight()
                    || oceanShape.getMaxY() - oceanShape.getHeight() >= getYPoint() + yDirection) {
            setYPoint(getYPoint() - yDirection);
        } else {
            setYPoint(getYPoint() + yDirection);
        }

        Point2D currentFishPoint = new Point2D.Double(getXPoint(), getYPoint());
        getContainer().setPoint(this.getExemplar(), currentFishPoint);

        holdNextStep();
    }

    @Override
    public void makeNewFish(){
        return;
    }

    public Color getColor() {
        return fishColor;
    }

    public void setOceanShape(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }

    public void setDrawFishes(List<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
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

    private void deathTimer(){
        Random random = new Random(System.currentTimeMillis());
        timer.schedule(new UnitTask(), random.nextInt(500000), 100);
    }

    private void killFish(){
        timer.cancel();
        logger.info("Exemplar " + this.getExemplar() + " is dead");
    }

    private class UnitTask extends TimerTask{
        @Override
        public void run() {
            killFish();
            drawFishes.remove(thisFish);
        }
    }

    private void holdNextStep() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
