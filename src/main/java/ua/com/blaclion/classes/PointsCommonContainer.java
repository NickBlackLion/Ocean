package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class PointsCommonContainer {
    private Map<Integer, Point2D> pointsOfObjectsInSea;
    private Map<Integer, OceanShape> whatObjectClass;
    private ReentrantLock lock;
    private Logger logger = Logger.getLogger(PointsCommonContainer.class);

    public PointsCommonContainer(){
        pointsOfObjectsInSea = new HashMap<>();
        whatObjectClass = new HashMap<>();
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

    public void setObject(int shapeExemplar, OceanShape oceanShape) {
        lock.lock();
        try {
            whatObjectClass.put(shapeExemplar, oceanShape);
        } finally {
            lock.unlock();
        }
    }

    public boolean isPointNear(Point2D currentPoint, Point2D futurePoint, OceanShape oceanShape) {
        lock.lock();
        try {
            OceanShape currentShape = oceanShape;

            for (Map.Entry<Integer, OceanShape> entry: whatObjectClass.entrySet()) {
                OceanShape oceanShapeFromContainer = entry.getValue();

                Point2D point2D = pointsOfObjectsInSea.get(entry.getKey());

                if (!currentPoint.equals(point2D) && CheckObjectNear.isObjectNear(futurePoint, point2D,
                        currentShape.getWidth(), currentShape.getHeight(),
                        oceanShapeFromContainer.getWidth(), oceanShapeFromContainer.getHeight())) {
                    return true;
                }
            }
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
