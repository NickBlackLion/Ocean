package ua.com.blaclion.classes;

import javafx.util.Pair;
import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckDirection {
    private static Logger logger = Logger.getLogger(CheckDirection.class);

    public static Pair<Integer, Integer> direction(Point2D currentPoint, Point2D pointFromContainer,
                                 int currentObjectWidth, int currentObjectHeight,
                                 int xDirection, int yDirection) {

        if (pointFromContainer.getX() - currentPoint.getX() >= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() <= currentObjectHeight) {
            if (xDirection < 0) {
                xDirection *= -1;
            }
            if (yDirection < 0 || yDirection > 0) {
                yDirection = 0;
            }
            logger.info("First direction");
            return new Pair<>(xDirection, yDirection);
        }

        if (pointFromContainer.getX() - currentPoint.getX() <= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() >= currentObjectHeight) {
            if (xDirection > 0 || xDirection < 0) {
                xDirection = 0;
            }
            if (yDirection < 0) {
                yDirection *= -1;
            }
            logger.info("Second direction");
            return new Pair<>(xDirection, yDirection);
        }

        if (pointFromContainer.getX() - currentPoint.getX() >= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() >= currentObjectHeight) {
            if (xDirection < 0) {
                xDirection *= -1;
            }
            if (yDirection < 0) {
                yDirection *= -1;
            }
            logger.info("Third direction");
            return new Pair<>(xDirection, yDirection);
        }

        //Next
        if (pointFromContainer.getX() - currentPoint.getX() <= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() >= currentObjectHeight) {
            if (xDirection > 0) {
                xDirection *= -1;
            }
            if (yDirection < 0 || yDirection > 0) {
                yDirection = 0;
            }
            logger.info("Fourth direction");
            return new Pair<>(xDirection, yDirection);
        }

        if (pointFromContainer.getX() - currentPoint.getX() >= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() <= currentObjectHeight) {
            if (xDirection > 0 || xDirection < 0) {
                xDirection = 0;
            }
            if (yDirection > 0) {
                yDirection *= -1;
            }
            logger.info("Fifth direction");
            return new Pair<>(xDirection, yDirection);
        }

        if (pointFromContainer.getX() - currentPoint.getX() <= currentObjectWidth
                && pointFromContainer.getY() - currentPoint.getY() <= currentObjectHeight) {
            if (xDirection > 0) {
                xDirection *= -1;
            }
            if (yDirection > 0) {
                yDirection *= -1;
            }
            logger.info("Sixth direction");
            return new Pair<>(xDirection, yDirection);
        }

        return null;
    }
}
