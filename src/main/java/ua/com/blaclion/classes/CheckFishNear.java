package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckFishNear {
    private static Logger logger = Logger.getLogger(CheckFishNear.class);

    public static boolean isFishNear(Fish prevFish, Fish currentFish) {
        Point2D currentFishPoint = new Point2D.Double(currentFish.getxPoint(), currentFish.getyPoint());
        Point2D prevFishPoint = new Point2D.Double(prevFish.getxPoint(), prevFish.getyPoint());
        return isFishAround(currentFishPoint, prevFishPoint,  currentFish);
    }

    public static boolean isFishNear(Point2D currentFishPoint, Point2D prevFishPoint, Fish fish) {
        return isFishAround(currentFishPoint, prevFishPoint, fish);
    }

    private static boolean isFishAround(Point2D currentFishPoint, Point2D prevFishPoint, Fish fish) {
        int width = fish.getFishWidth()*2;
        int height = fish.getFishHeight()*2;

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