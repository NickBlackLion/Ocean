package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.*;

public class Rock extends OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color rockColor;
    private int yPoint;

    public Rock() {
        rockColor = Color.gray;
        setWidth(70);
        setHeight(50);
    }

    @Override
    public Color getColor() {
        return rockColor;
    }

    @Override
    public void setYPoint(int yPoint, int delta) {
        this.yPoint = yPoint - (delta/2);
        logger.info("yPoint = " + this.yPoint);
    }

    @Override
    public int getYPoint() {
        return yPoint;
    }
}
