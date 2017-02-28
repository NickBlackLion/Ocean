package ua.com.blaclion.abstract_classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Created by nick on 26.02.17.
 */
public abstract class OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private int width;
    private int height;
    private int xPoint = 0;
    private int yPoint = 0;
    private Random xStartPoint = new Random(System.currentTimeMillis()*2);
    private Random yStartPoint = new Random(System.currentTimeMillis()*5);
    private Rectangle2D oceanSize;
    private static int counter = 1;
    private final int exemplar = counter++;

    public void setXPoint(int xPoint) {
        if (this.xPoint == 0) {
            int transitXPoint = xStartPoint.nextInt(xPoint);

            if (oceanSize.getMaxX() <= transitXPoint + this.getWidth()) {
                this.xPoint = transitXPoint - this.getWidth();
            } else {
                this.xPoint = transitXPoint;
            }
        } else {
            this.xPoint = xPoint;
        }

        logger.info("xPoint = " + this.xPoint);
    }

    public int getXPoint(){
        return xPoint;
    }

    public void setYPoint(int yPoint, int delta) {
        if (this.yPoint == 0) {
            int transitYPoint = yStartPoint.nextInt(yPoint) + delta;
            if (oceanSize.getMaxY() <= transitYPoint + this.getHeight()) {
                this.yPoint = transitYPoint - this.getHeight();
            } else {
                this.yPoint = transitYPoint;
            }
        } else {
            this.yPoint = yPoint;
        }

        logger.info("yPoint = " + this.yPoint);
    }

    public int getYPoint() {
        return yPoint;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setOceanSize(Rectangle2D oceanSize) {
        this.oceanSize = oceanSize;
    }

    public int getExemplar() {
        return exemplar;
    }

    public abstract Color getColor();
}
