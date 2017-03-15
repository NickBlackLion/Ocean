package ua.com.blaclion.classes;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Common class-container for all objects in the sea
 */
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

    /**
     * Method checks is some another ocean object near current object
     * @param currentPoint point of current object
     * @param oceanShape current checked object
     * @return
     */
    public boolean isSomeObjectNear(Point2D currentPoint, OceanShape oceanShape) {
        lock.lock();
        try {
            for (Map.Entry<Integer, OceanShape> entry: whatObjectClass.entrySet()) {
                OceanShape oceanShapeFromContainer = entry.getValue();

                Point2D point2D = pointsOfObjectsInSea.get(entry.getKey());

                if (currentPoint != point2D && CheckObjectNear.isObjectNear(currentPoint, point2D,
                        oceanShape, oceanShapeFromContainer)) {
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }

        return false;
    }

    /**
     * Method checks is some another ocean object near future point of current object
     * @param currentPoint point of current object
     * @param futurePoint future point of current object
     * @param oceanShape current checked object
     * @return
     */
    public boolean isFuturePosNearSomeObject(Point2D currentPoint, Point2D futurePoint, OceanShape oceanShape) {
        lock.lock();
        try {
            for (Map.Entry<Integer, OceanShape> entry: whatObjectClass.entrySet()) {
                OceanShape oceanShapeFromContainer = entry.getValue();

                Point2D point2D = pointsOfObjectsInSea.get(entry.getKey());

                if (!currentPoint.equals(point2D) && CheckObjectNear.isObjectNear(futurePoint, point2D,
                        oceanShape, oceanShapeFromContainer)) {
                    return true;
                }
            }
        } finally {
            lock.unlock();
        }

        return false;
    }

    public Pair<Point2D, OceanShape> makeNewPredatorPath(Point2D currentPoint, PredatorFish predatorFish) {
        lock.lock();
        try {
            for (Map.Entry<Integer, OceanShape> entry: whatObjectClass.entrySet()) {
                if (entry.getValue().getClass() == GoldFish.class) {
                    Point2D point = pointsOfObjectsInSea.get(entry.getKey());
                    if (CheckObjectNear.isObjectAroundAccuracyPredator(currentPoint, point, predatorFish, entry.getValue())) {
                        logger.info(currentPoint + " " + point);
                        return new Pair<>(point, entry.getValue());
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }

        return null;
    }

    public void remove(int exemplar) {
        lock.lock();
        try {
            whatObjectClass.remove(exemplar);
            pointsOfObjectsInSea.remove(exemplar);
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
