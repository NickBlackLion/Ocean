package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Class that set up starts coordinates and corrects them if they lay not int the ocean shape
 */
public class SetCoordinate {
    private Rectangle2D oceanSize;
    private OceanShape oceanShape;
    private int oceanHeightDelta;
    private int moveDelta;
    private MainFrame mainFrame;
    private int pointUnbounded;
    private int transitPoint;
    private boolean checkMaxXBound;
    private boolean checkMaxYBound;

    private Logger logger = Logger.getLogger(this.getClass());

    public SetCoordinate(MainFrame mainFrame, Rectangle2D oceanSize, OceanShape oceanShape, int oceanHeightDelta) {
        this.mainFrame = mainFrame;
        this.oceanSize = oceanSize;
        this.oceanShape = oceanShape;
        this.oceanHeightDelta = oceanHeightDelta;
        moveDelta = 10;
        checkMaxXBound = false;
        checkMaxYBound = false;
    }

    /**
     * Method that set up start X coordinate in shape of the ocean
     */
    public void setStartX () {
        Random xStartPoint = new Random(System.currentTimeMillis() * 2);
        transitPoint = xStartPoint.nextInt((int) oceanSize.getMaxX());
        pointUnbounded = transitPoint + oceanShape.getWidth();

        checkXBounds();
        oceanShape.setXPoint(transitPoint);


        logger.info(oceanShape.getClass().getSimpleName() + " exemplar " + oceanShape.getExemplar() + " point x " + oceanShape.getXPoint());
    }

    /**
     * Method that set up start Y coordinate in shape of the ocean
     */
    public void setStartY () {
        Random yStartPoint = new Random(System.currentTimeMillis() * 5);
        transitPoint = yStartPoint.nextInt((int) oceanSize.getMaxY() + oceanHeightDelta);

        if (oceanShape.getClass() == Rock.class) {
            oceanShape.setYPoint((int)oceanSize.getMaxY() - oceanShape.getHeight()/2);
        } else {
            pointUnbounded = transitPoint + oceanShape.getHeight();

            checkYBounds();
            oceanShape.setYPoint(transitPoint);
        }

        logger.info(oceanShape.getClass().getSimpleName() + " exemplar " + oceanShape.getExemplar() + " point y " + oceanShape.getYPoint());
    }

    /**
     * Method that corrects current X coordinate in shape of the ocean
     * for not intersect with other objects
     */
    public void correctXCoordinate() {
        int move = moveDelta;

        pointUnbounded = oceanShape.getXPoint() + oceanShape.getWidth();

        checkXBounds();

        if (checkMaxXBound) {
            move *= -1;
        }

        transitPoint = oceanShape.getXPoint() + move;

        oceanShape.setXPoint(transitPoint);

        logger.info(oceanShape.getXPoint());
    }

    /**
     * Method that corrects current Y coordinate in shape of the ocean
     * for not intersect with other objects
     */
    public void correctYCoordinate() {
        int move = moveDelta;

        pointUnbounded = oceanShape.getYPoint() + oceanShape.getHeight();

        checkYBounds();

        if (checkMaxYBound) {
            move *= -1;
        }

        transitPoint = oceanShape.getYPoint() + move;

        oceanShape.setYPoint(transitPoint);

        logger.info(oceanShape.getYPoint());
    }

    /**
     * Method that checks bounds and corrects while X coordinate
     * if it is out of the ocean width
     */
    private void checkXBounds() {
        while (oceanSize.getMaxX() < pointUnbounded) {
            pointUnbounded -= moveDelta;
            transitPoint -= moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanSize.getMaxX() < pointUnbounded transitPoint = " + transitPoint);
            checkMaxXBound = true;
        }

        while (oceanSize.getMinX() > transitPoint) {
            transitPoint += moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanSize.getMinX() > transitPoint transitPoint = " + transitPoint);
        }
    }

    /**
     * Method that checks bounds and corrects while Y coordinate
     * if it is out of the ocean width
     */
    private void checkYBounds() {
        while (mainFrame.getHeight() < pointUnbounded) {
            pointUnbounded -= moveDelta;
            transitPoint -= moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " mainFrame.getHeight() < pointUnbounded transitPoint = " + transitPoint);
            checkMaxYBound = true;
        }

        while (oceanHeightDelta + oceanShape.getHeight() > transitPoint) {
            transitPoint += moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanHeightDelta + oceanShape.getHeight() > transitPoint transitPoint = " + transitPoint);
        }
    }
}
