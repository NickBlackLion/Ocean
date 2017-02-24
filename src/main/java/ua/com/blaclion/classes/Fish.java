package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Random;

public class Fish {
    private int fishWidth;
    private int fishHeight;
    private int xPoint;
    private int yPoint;
    private Random xStartPoint;
    private Random yStartPoint;
    private Logger logger = Logger.getLogger(this.getClass());
    private Color fishColor;

    public Fish() {
        xStartPoint = new Random(System.currentTimeMillis()*2);
        yStartPoint = new Random(System.currentTimeMillis()*5);

        xPoint = 0;
        yPoint = 0;

        fishWidth = 20;
        fishHeight = 10;

        fishColor = new Color(255,215,0);
    }

    public void swim(int xDirection, int yDirection){
        xPoint += xDirection;
        yPoint += yDirection;
    }

    public Fish makeNewFish(){
        return new Fish();
    }

    public int getFishWidth() {
        return fishWidth;
    }

    public void setFishWidth(int fishWidth) {
        this.fishWidth = fishWidth;
    }

    public int getFishHeight() {
        return fishHeight;
    }

    public void setFishHeight(int fishHeight) {
        this.fishHeight = fishHeight;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        if (this.xPoint == 0) {
            this.xPoint = xStartPoint.nextInt(xPoint);
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
            this.yPoint = yStartPoint.nextInt(yPoint) + delta;
        } else {
            this.yPoint = yPoint;
        }

        logger.info("yPoint = " + this.yPoint);
    }

    public Color getFishColor() {
        return fishColor;
    }
}
