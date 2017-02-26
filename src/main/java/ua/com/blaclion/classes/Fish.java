package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private static int counter = 0;
    private int exemplar;
    private Timer timer = new Timer();

    public Fish() {
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

        //deathTimer();
    }

    public void swim(){
        int xDirection = (int)(Math.random()*20 - 5);
        int yDirection = (int)(Math.random()*20 - 5);

        for (DrawFish drawFish: drawFishes){
            if(!drawFish.getFish().equals(this)){
                if (CheckFishNear.isCanMove(drawFish.getFish(), this, xDirection, yDirection)){
                    if (oceanShape.getMaxX() <= xPoint + xDirection + this.getFishWidth()) {
                        xPoint -= xDirection;
                    } else {
                        xPoint += xDirection;
                    }

                    if (oceanShape.getMaxY() <= yPoint + yDirection + this.getFishHeight()) {
                        yPoint -= yDirection;
                    } else {
                        yPoint += yDirection;
                    }
                }
            }
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
        timer.schedule(new UnitTask(), (int) (Math.random()*50000), 100);
    }

    private void killFish(){
        fishWidth = 0;
        fishHeight = 0;
        timer.cancel();
    }

    private class UnitTask extends TimerTask{
        @Override
        public void run() {
            killFish();
        }
    }
}
