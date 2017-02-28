package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PointsCommonContainer {
    private Map<Integer, Point2D> pointsOfObjectsInSea;
    private ReentrantLock lock;
    private Logger logger = Logger.getLogger(PointsCommonContainer.class);

    public PointsCommonContainer(){
        pointsOfObjectsInSea = new HashMap<>();
        lock = new ReentrantLock();
    }

    public void setPoint(int fishExemplar, Point2D point){
        lock.lock();
        try {
            pointsOfObjectsInSea.put(fishExemplar, point);
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isEqualsPoint(Point2D futurePoint, Point2D currentPoint, int width, int height){
        lock.lock();
        try {
            for (Point2D point2D: pointsOfObjectsInSea.values()){
                if (!currentPoint.equals(point2D)) {
                    if (CheckFishNear.isFishNear(futurePoint, point2D, width, height)) {
                        logger.info("Point " + futurePoint + " point2D " + point2D);
                        return true;
                    }
                }
            }

            return false;
        }
        finally {
            lock.unlock();
        }
    }

    public void printContainer(){
        lock.lock();
        try {
            for (Map.Entry<Integer, Point2D> entry: pointsOfObjectsInSea.entrySet()){
                logger.info("Key = " + entry.getKey() + " value = " + entry.getValue());
            }

            System.out.println();
        } finally {
            lock.unlock();
        }
    }
}
