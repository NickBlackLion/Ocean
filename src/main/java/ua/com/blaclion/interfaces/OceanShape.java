package ua.com.blaclion.interfaces;

import java.awt.*;

/**
 * Created by nick on 26.02.17.
 */
public interface OceanShape {
    void setXPoint(int xPoint);
    int getXPoint();

    void setYPoint(int yPoint, int delta);
    int getYPoint();

    int getWidth();
    int getHeight();

    Color getColor();
}
