package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Fish {
    private int fishWidth;
    private int fishHeight;
    private int xPoint;
    private int yPoint;
    private Random xStartPoint;
    private Random yStartPoint;
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;
    private Rectangle2D oceanShape;
    private java.util.List<DrawFish> drawFishes;
    private static int counter = 1;
    private int exemplar;
    private Timer timer;
    private boolean deathTimerStart = false;
    private DrawFish thisFish;

    public Fish() {
        timer = new Timer();

        xStartPoint = new Random(System.currentTimeMillis()*2);
        yStartPoint = new Random(System.currentTimeMillis()*5);

        xPoint = 0;
        yPoint = 0;

        fishWidth = 20;
        fishHeight = 10;

        fishColor = new Color(255,215,0);

        timer = new Timer();

        exemplar = counter++;

        logger.info("counter = " + counter);
    }

    public void swim(){
        int xDirection = (int)(Math.random()*20 - 10);
        int yDirection = (int)(Math.random()*20 - 10);

        if(!deathTimerStart){
            deathTimer();
            deathTimerStart = true;
        }

        for (DrawFish drawFish: drawFishes){
            if (!drawFish.getFish().equals(this)){
                if (!CheckFishNear.isCanMove(drawFish.getFish(), this, xDirection, yDirection)){
                    xDirection = 0;
                    yDirection = 0;
                    logger.info("In current position");
                }
            } else {
                thisFish = drawFish;
            }
        }

        if (oceanShape.getMaxX() <= xPoint + xDirection + this.getFishWidth()
                    || oceanShape.getMaxX() - oceanShape.getWidth() >= xPoint + xDirection) {
            xPoint -= xDirection;
        } else {
            xPoint += xDirection;
        }

        if (oceanShape.getMaxY() <= yPoint + yDirection + this.getFishHeight()
                    || oceanShape.getMaxY() - oceanShape.getHeight() >= yPoint + yDirection) {
            yPoint -= yDirection;
            logger.info("yPoint -= yDirection; " + yPoint
                    + " oceanShape.getMaxY() " + oceanShape.getMaxY()
                    + " oceanShape.getHeight() " + oceanShape.getHeight()
                    + " exemplar " + this.getExemplar());
        } else {
            yPoint += yDirection;
        }

        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Fish makeNewFish(){
        return new Fish();
    }

    public int getFishWidth() {
        return fishWidth;
    }

    public int getFishHeight() {
        return fishHeight;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        if (this.xPoint == 0) {
            int transitXPoint = xStartPoint.nextInt(xPoint);

            if (oceanShape.getMaxX() <= transitXPoint + this.getFishWidth()) {
                this.xPoint = transitXPoint - this.getFishWidth();
            } else {
                this.xPoint = transitXPoint;
            }
        } else {
            this.xPoint = xPoint;
        }

        logger.info("xPoint = " + this.xPoint);
    }

    public int getyPoint() {
        return yPoint;
    }

    public void setyPoint(int yPoint, int delta) {
        if (this.yPoint == 0) {
            int transitYPoint = yStartPoint.nextInt(yPoint) + delta;
            if (oceanShape.getMaxY() <= transitYPoint + this.getFishHeight()) {
                this.yPoint = transitYPoint - this.getFishHeight();
            } else {
                this.yPoint = transitYPoint;
            }
        } else {
            this.yPoint = yPoint;
        }

        logger.info("yPoint = " + this.yPoint);
    }

    public Color getFishColor() {
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

        Fish fish = (Fish) obj;

        if (fish.getxPoint() != getxPoint()){
            return false;
        }

        if (fish.getyPoint() != getyPoint()){
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Xpoint = " + this.getxPoint() + " Ypoint = " + this.getyPoint();
    }

    public int getExemplar() {
        return exemplar;
    }

    private void deathTimer(){
        Random random = new Random(System.currentTimeMillis());
        timer.schedule(new UnitTask(), random.nextInt(500000), 100);
    }

    private void killFish(){
        fishWidth = 0;
        fishHeight = 0;
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
}
