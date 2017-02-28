package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.interfaces.OceanShape;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Rock implements OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private int xPoint;
    private int yPoint;
    private int width;
    private int height;
    private Rectangle2D oceanShape;
    private Random xStartPoint;
    private Random yStartPoint;

    public Rock() {
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
    public int getXPoint() {
        return xPoint;
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

    @Override
    public int getYPoint() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    public void setOceanShape(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }
}
