package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckObjectNear {
    private static Logger logger = Logger.getLogger(CheckObjectNear.class);

    public static boolean isObjectNear(Point2D currentPoint, Point2D prevPoint, int width, int height) {
        return isObjectAround(currentPoint, prevPoint, width, height);
    }

    private static boolean isObjectAround(Point2D currentPoint, Point2D prevPoint, int shapeWidth, int shapeHeight) {
        int width = shapeWidth;
        int height = shapeHeight;

        if (currentPoint.getX() >= prevPoint.getX()
                && currentPoint.getX() <= prevPoint.getX() + width
                && currentPoint.getY() >= prevPoint.getY()
                && currentPoint.getY() <= prevPoint.getY() + height) {
            logger.info("First if " + currentPoint + " " + prevPoint);
            return true;
        }

        if (currentPoint.getX() + width >= prevPoint.getX()
                && currentPoint.getX() + width <= prevPoint.getX() + width
                && currentPoint.getY() >= prevPoint.getY()
                && currentPoint.getY() <= prevPoint.getY() + height){
            logger.info("Second if " + currentPoint + " " + prevPoint);
            return true;
        }

        if (currentPoint.getX() >= prevPoint.getX()
                && currentPoint.getX() <= prevPoint.getX() + width
                && currentPoint.getY() + height >= prevPoint.getY()
                && currentPoint.getY() + height <= prevPoint.getY() + height){
            logger.info("Third if " + currentPoint + " " + prevPoint);
            return true;
        }

        if (currentPoint.getX() + width >= prevPoint.getX()
                && currentPoint.getX() + width <= prevPoint.getX() + width
                && currentPoint.getY() + height >= prevPoint.getY()
                && currentPoint.getY() + height <= prevPoint.getY() + height){
            logger.info("Fourth if " + currentPoint + " " + prevPoint);
            return true;
        }

        return false;
    }
}