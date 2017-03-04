package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckObjectNear {
    private static Logger logger = Logger.getLogger(CheckObjectNear.class);

    public static boolean isObjectNear(Point2D currentFishPoint, Point2D prevFishPoint, int width, int height) {
        return isObjectAround(currentFishPoint, prevFishPoint, width, height);
    }

    private static boolean isObjectAround(Point2D currentFishPoint, Point2D prevFishPoint, int shapeWidth, int shapeHeight) {
        int width = shapeWidth*2;
        int height = shapeHeight*2;

        if (currentFishPoint.getX() >= prevFishPoint.getX()
                && currentFishPoint.getX() <= prevFishPoint.getX() + width
                && currentFishPoint.getY() >= prevFishPoint.getY()
                && currentFishPoint.getY() <= prevFishPoint.getY() + height) {
            logger.info("First if");
            return true;
        }

        if (currentFishPoint.getX() + width >= prevFishPoint.getX()
                && currentFishPoint.getX() + width <= prevFishPoint.getX() + width
                && currentFishPoint.getY() >= prevFishPoint.getY()
                && currentFishPoint.getY() <= prevFishPoint.getY() + height){
            logger.info("Second if");
            return true;
        }

        if (currentFishPoint.getX() >= prevFishPoint.getX()
                && currentFishPoint.getX() <= prevFishPoint.getX() + width
                && currentFishPoint.getY() + height >= prevFishPoint.getY()
                && currentFishPoint.getY() + height <= prevFishPoint.getY() + height){
            logger.info("Third if");
            return true;
        }

        if (currentFishPoint.getX() + width >= prevFishPoint.getX()
                && currentFishPoint.getX() + width <= prevFishPoint.getX() + width
                && currentFishPoint.getY() + height >= prevFishPoint.getY()
                && currentFishPoint.getY() + height <= prevFishPoint.getY() + height){
            logger.info("Fourth if");
            return true;
        }

        return false;
    }
}