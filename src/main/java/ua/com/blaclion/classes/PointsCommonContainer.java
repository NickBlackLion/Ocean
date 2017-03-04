package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class PointsCommonContainer {
    private Map<Integer, Point2D> pointsOfObjectsInSea;
    private Set<Class> setOfClasses;
    private ReentrantLock lock;
    private Logger logger = Logger.getLogger(PointsCommonContainer.class);

    public PointsCommonContainer(){
        pointsOfObjectsInSea = new HashMap<>();
        setOfClasses = new HashSet<>();
        lock = new ReentrantLock();
    }

    public void setPoint(int shapeExemplar, Point2D point){
        lock.lock();
        try {
            pointsOfObjectsInSea.put(shapeExemplar, point);
        } finally {
            lock.unlock();
        }
    }

    public void setClass(Class<?> classOfObjectInSea){
        lock.lock();
        try {
            setOfClasses.add(classOfObjectInSea);
        }
        finally {
            lock.unlock();
        }
    }

    public boolean isPointNear(Point2D futurePoint, Point2D currentPoint, int width, int height){
        lock.lock();
        try {
            for (Point2D point2D: pointsOfObjectsInSea.values()){
                if (!currentPoint.equals(point2D) && CheckObjectNear.isObjectNear(futurePoint, point2D, width, height)) {
                    logger.info("Point " + futurePoint + " point2D " + point2D);
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }

        return false;
    }

    public boolean isPointNear(Point2D currentPoint) {
        lock.lock();
        try {
            OceanShape oceanShape = null;

            for (Class<?> classOfObj: setOfClasses) {
                if (classOfObj == Rock.class) {
                    oceanShape = (Rock) classOfObj.newInstance();
                }

                if (classOfObj == GoldFish.class) {
                    oceanShape = (GoldFish) classOfObj.newInstance();
                }

                for (Point2D point2D : pointsOfObjectsInSea.values()) {
                    if (!currentPoint.equals(point2D) && CheckObjectNear.isObjectNear(currentPoint, point2D,
                            oceanShape.getWidth(), oceanShape.getHeight())) {
                        return true;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return false;
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
