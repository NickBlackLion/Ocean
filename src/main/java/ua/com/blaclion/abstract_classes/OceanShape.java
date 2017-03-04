package ua.com.blaclion.abstract_classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.classes.PointsCommonContainer;

import java.awt.*;

public abstract class OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private int width;
    private int height;
    private int xPoint = 0;
    private int yPoint = 0;
    private PointsCommonContainer container;
    private static int counter = 1;
    private final int exemplar = counter++;

    public void setXPoint(int xPoint) {
        this.xPoint = xPoint;
    }

    public int getXPoint(){
        return xPoint;
    }

    public void setYPoint(int yPoint) {
        this.yPoint = yPoint;
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

    public int getExemplar() {
        return exemplar;
    }

    public void setContainer(PointsCommonContainer container) {
        this.container = container;
    }

    public PointsCommonContainer getContainer() {
        return container;
    }

    public abstract Color getColor();
}
