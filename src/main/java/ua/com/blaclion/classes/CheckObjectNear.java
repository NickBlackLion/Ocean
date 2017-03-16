package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.geom.Point2D;

/**
 * Class with static method that checks is some object near current object
 */
public class CheckObjectNear {
    private static Logger logger = Logger.getLogger(CheckObjectNear.class);

    public static boolean isObjectNear(Point2D futurePoint, Point2D pointFromContainer, OceanShape currentFigure,
                                       OceanShape figureFromContainer) {
        return isObjectAround(futurePoint, pointFromContainer, currentFigure, figureFromContainer);
    }

    private static boolean isObjectAroundAccuracy(Point2D futurePoint, Point2D pointFromContainer,
                                                  OceanShape currentFigure, OceanShape figureFromContainer) {

        for (int i = 0; i < currentFigure.getWidth(); i++) {
            for (int j = 0; j < currentFigure.getHeight(); j++) {
                Point2D currentDelta = new Point2D.Double(futurePoint.getX() + i, futurePoint.getY() + j);
                for (int k = 0; k < figureFromContainer.getWidth(); k++){
                    for (int r = 0; r < figureFromContainer.getHeight(); r++) {
                        Point2D prevDelta = new Point2D.Double(pointFromContainer.getX() + k, pointFromContainer.getY() + r);
                        if (currentDelta.getX() == prevDelta.getX() && currentDelta.getY() == prevDelta.getY()) {
                            logger.info(String.format("True i = %d j = %d k = %d r = %d", i, j, k, r));
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private static boolean isObjectAround(Point2D futurePoint, Point2D pointFromContainer,
                                          OceanShape currentFigure, OceanShape figureFromContainer) {
        int buff = 100;

        if (futurePoint.getX() >= pointFromContainer.getX()
                && futurePoint.getX() <= pointFromContainer.getX() + buff
                && futurePoint.getY() >= pointFromContainer.getY()
                && futurePoint.getY() <= pointFromContainer.getY() + buff) {
            logger.info("First if " + futurePoint + " " + pointFromContainer);
            return isObjectAroundAccuracy(futurePoint, pointFromContainer, currentFigure, figureFromContainer);
        }

        if (futurePoint.getX() + buff >= pointFromContainer.getX()
                && futurePoint.getX() + buff <= pointFromContainer.getX() + buff
                && futurePoint.getY() >= pointFromContainer.getY()
                && futurePoint.getY() <= pointFromContainer.getY() + buff){
            logger.info("Second if " + futurePoint + " " + pointFromContainer);
            return isObjectAroundAccuracy(futurePoint, pointFromContainer, currentFigure, figureFromContainer);
        }

        if (futurePoint.getX() >= pointFromContainer.getX()
                && futurePoint.getX() <= pointFromContainer.getX() + buff
                && futurePoint.getY() + buff >= pointFromContainer.getY()
                && futurePoint.getY() + buff <= pointFromContainer.getY() + buff){
            logger.info("Third if " + futurePoint + " " + pointFromContainer);
            return isObjectAroundAccuracy(futurePoint, pointFromContainer, currentFigure, figureFromContainer);
        }

        if (futurePoint.getX() + buff >= pointFromContainer.getX()
                && futurePoint.getX() + buff <= pointFromContainer.getX() + buff
                && futurePoint.getY() + buff >= pointFromContainer.getY()
                && futurePoint.getY() + buff <= pointFromContainer.getY() + buff){
            logger.info("Fourth if " + futurePoint + " " + pointFromContainer);
            return isObjectAroundAccuracy(futurePoint, pointFromContainer, currentFigure, figureFromContainer);
        }

        return false;
    }

    public static boolean isGoldFishAroundPredator(Point2D currentPredatorPoint, Point2D pointFromContainer,
                                                  OceanShape predatorShape, OceanShape shapeFromContainer) {

        Point2D point2D = new Point2D.Double(currentPredatorPoint.getX() - predatorShape.getWidth(),
                currentPredatorPoint.getY() - predatorShape.getHeight());

        for (int i = 0; i < predatorShape.getWidth(); i++) {
            for (int j = 0; j < predatorShape.getHeight(); j++) {
                Point2D currentDelta = new Point2D.Double(point2D.getX() + i, point2D.getY() + j);
                if (currentDelta.getX() >= pointFromContainer.getX()
                        && currentDelta.getX() <= pointFromContainer.getX() + shapeFromContainer.getWidth()
                        && currentDelta.getY() >= pointFromContainer.getY()
                        && currentDelta.getY() <= pointFromContainer.getY() + shapeFromContainer.getHeight()) {
                    return true;
                }

                if (currentDelta.getX() + predatorShape.getWidth() >= pointFromContainer.getX()
                        && currentDelta.getX() + predatorShape.getWidth() <= pointFromContainer.getX() + shapeFromContainer.getWidth()
                        && currentDelta.getY() >= pointFromContainer.getY()
                        && currentDelta.getY() <= pointFromContainer.getY() + shapeFromContainer.getHeight()){
                    return true;
                }

                if (currentDelta.getX() >= pointFromContainer.getX()
                        && currentDelta.getX() <= pointFromContainer.getX() + shapeFromContainer.getWidth()
                        && currentDelta.getY() + predatorShape.getHeight() >= pointFromContainer.getY()
                        && currentDelta.getY() + predatorShape.getHeight() <= pointFromContainer.getY() + shapeFromContainer.getHeight()){
                    return true;
                }

                if (currentDelta.getX() + predatorShape.getWidth() >= pointFromContainer.getX()
                        && currentDelta.getX() + predatorShape.getWidth() <= pointFromContainer.getX() + shapeFromContainer.getWidth()
                        && currentDelta.getY() + predatorShape.getHeight() >= pointFromContainer.getY()
                        && currentDelta.getY() + predatorShape.getHeight() <= pointFromContainer.getY() + shapeFromContainer.getHeight()){
                    return true;
                }
            }
        }

        return false;
    }
}