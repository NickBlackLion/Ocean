package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.geom.Point2D;

public class CheckObjectNear {
    private static Logger logger = Logger.getLogger(CheckObjectNear.class);

    public static boolean isObjectNear(Point2D currentPoint, Point2D prevPoint,
                                       int currObjWidth, int currObjHeight,
                                       int prevObjWidth, int prevObjHeight) {
        return isObjectAround2(currentPoint, prevPoint, currObjWidth, currObjHeight, prevObjWidth, prevObjHeight);
    }

    private static boolean isObjectAround2(Point2D currentPoint, Point2D prevPoint,
                                           int currObjWidth, int currObjHeight,
                                           int prevObjWidth, int prevObjHeight) {

        for (int i = 0; i < currObjWidth; i++) {
            for (int j = 0; j < currObjHeight; j++) {
                Point2D currentDelta = new Point2D.Double(currentPoint.getX() + i, currentPoint.getY() + j);
                for (int k = 0; k < prevObjWidth; k++){
                    for (int r = 0; r < prevObjHeight; r++) {
                        Point2D prevDelta = new Point2D.Double(prevPoint.getX() + k, prevPoint.getY() + r);
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
}