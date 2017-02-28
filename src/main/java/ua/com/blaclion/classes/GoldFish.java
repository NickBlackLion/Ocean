package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.interfaces.Fish;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GoldFish implements Fish {
    private int width;
    private int height;
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
    private PointsCommonContainer pointContainer;

    public GoldFish() {
        timer = new Timer();

        xStartPoint = new Random(System.currentTimeMillis()*2);
        yStartPoint = new Random(System.currentTimeMillis()*5);

        xPoint = 0;
        yPoint = 0;

        width = 20;
        height = 10;

        fishColor = new Color(255,215,0);

        timer = new Timer();

        exemplar = counter++;

        logger.info("counter = " + counter);
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

        Point2D fishNextPoint = new Point2D.Double(this.getXPoint() + xDirection, this.getYPoint() + yDirection);
        Point2D fishCurrentPoint = new Point2D.Double(this.getXPoint(), this.getYPoint());

        if (pointContainer.isEqualsPoint(fishNextPoint, fishCurrentPoint, this.getWidth(), this.getHeight())){
            xDirection = 0;
            yDirection = 0;
            logger.info("It's existed fish near exemplar " + this.getExemplar());

            holdNextStep();
        }

        if (oceanShape.getMaxX() <= xPoint + xDirection + this.getWidth()
                    || oceanShape.getMaxX() - oceanShape.getWidth() >= xPoint + xDirection) {
            xPoint -= xDirection;
        } else {
            xPoint += xDirection;
        }

        if (oceanShape.getMaxY() <= yPoint + yDirection + this.getHeight()
                    || oceanShape.getMaxY() - oceanShape.getHeight() >= yPoint + yDirection) {
            yPoint -= yDirection;
            logger.info("yPoint -= yDirection; " + yPoint
                    + " oceanShape.getMaxY() " + oceanShape.getMaxY()
                    + " oceanShape.getHeight() " + oceanShape.getHeight()
                    + " exemplar " + this.getExemplar());
        } else {
            yPoint += yDirection;
        }

        Point2D currentFishPoint = new Point2D.Double(xPoint, yPoint);
        pointContainer.setPoint(this.getExemplar(), currentFishPoint);

        holdNextStep();
    }

    @Override
    public void makeNewFish(){
        return;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getXPoint() {
        return xPoint;
    }

    @Override
    public void setXPoint(int xPoint) {
        if (this.xPoint == 0) {
            int transitXPoint = xStartPoint.nextInt(xPoint);

            if (oceanShape.getMaxX() <= transitXPoint + this.getWidth()) {
                this.xPoint = transitXPoint - this.getWidth();
            } else {
                this.xPoint = transitXPoint;
            }
        } else {
            this.xPoint = xPoint;
        }

        logger.info("xPoint = " + this.xPoint);
    }

    @Override
    public int getYPoint() {
        return yPoint;
    }

    @Override
    public void setYPoint(int yPoint, int delta) {
        if (this.yPoint == 0) {
            int transitYPoint = yStartPoint.nextInt(yPoint) + delta;
            if (oceanShape.getMaxY() <= transitYPoint + this.getHeight()) {
                this.yPoint = transitYPoint - this.getHeight();
            } else {
                this.yPoint = transitYPoint;
            }
        } else {
            this.yPoint = yPoint;
        }

        logger.info("yPoint = " + this.yPoint);
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

    public int getExemplar() {
        return exemplar;
    }

    public void setPointContainer(PointsCommonContainer pointContainer) {
        this.pointContainer = pointContainer;
    }

    private void deathTimer(){
        Random random = new Random(System.currentTimeMillis());
        timer.schedule(new UnitTask(), random.nextInt(500000), 100);
    }

    private void killFish(){
        width = 0;
        height = 0;
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
