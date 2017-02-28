package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckFishNear {
    private static Logger logger = Logger.getLogger(CheckFishNear.class);

    /*
    public static boolean isFishNear(GoldFish prevFish, GoldFish currentFish) {
        Point2D currentFishPoint = new Point2D.Double(currentFish.getXPoint(), currentFish.getYPoint());
        Point2D prevFishPoint = new Point2D.Double(prevFish.getXPoint(), prevFish.getYPoint());
        return isFishAround(currentFishPoint, prevFishPoint,  currentFish);
    }
    */

    public static boolean isFishNear(Point2D currentFishPoint, Point2D prevFishPoint, int width, int height) {
        return isFishAround(currentFishPoint, prevFishPoint, width, height);
    }

    private static boolean isFishAround(Point2D currentFishPoint, Point2D prevFishPoint, int shapeWidth, int shapeHeight) {
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