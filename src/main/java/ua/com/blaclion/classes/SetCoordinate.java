package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Rectangle2D;
import java.util.Random;

public class SetCoordinate {
    private Rectangle2D oceanSize;
    private OceanShape oceanShape;
    private int oceanHeightDelta;
    private int moveDelta;
    private MainFrame mainFrame;

    private Logger logger = Logger.getLogger(this.getClass());

    public SetCoordinate(MainFrame mainFrame, Rectangle2D oceanSize, OceanShape oceanShape, int oceanHeightDelta) {
        this.mainFrame = mainFrame;
        this.oceanSize = oceanSize;
        this.oceanShape = oceanShape;
        this.oceanHeightDelta = oceanHeightDelta;
        moveDelta = 10;
    }

    public void setStartX () {
        Random xStartPoint = new Random(System.currentTimeMillis() * 2);
        int transitXPoint = xStartPoint.nextInt((int) oceanSize.getMaxX());
        int pointUnbounded = transitXPoint + oceanShape.getWidth();

        oceanShape.setXPoint(checkXBounds(pointUnbounded, transitXPoint));

        logger.info(oceanShape.getClass().getSimpleName() + " exemplar " + oceanShape.getExemplar() + " point x " + oceanShape.getXPoint());
    }

    public void setStartY () {
        Random yStartPoint = new Random(System.currentTimeMillis() * 5);
        int transitYPoint = yStartPoint.nextInt((int) oceanSize.getMaxY() + oceanHeightDelta);

        if (oceanShape.getClass() == Rock.class) {
            oceanShape.setYPoint((int)oceanSize.getMaxY() - oceanShape.getHeight()/2);
        } else {
            int pointUnbounded = transitYPoint + oceanShape.getHeight();

            oceanShape.setYPoint(checkYBounds(pointUnbounded, transitYPoint));

            logger.info(oceanShape.getClass().getSimpleName() + " exemplar " + oceanShape.getExemplar() + " point y " + oceanShape.getYPoint());
        }
    }

    private int checkXBounds(int pointUnbounded, int transitXPoint) {
        while (oceanSize.getMaxX() < pointUnbounded) {
            pointUnbounded -= moveDelta;
            transitXPoint -= moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanSize.getMaxX() < pointUnbounded transitXPoint = " + transitXPoint);
        }

        while (oceanSize.getMinX() > transitXPoint) {
            transitXPoint += moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanSize.getMaxX() < pointUnbounded transitXPoint = " + transitXPoint);
        }

        return transitXPoint;
    }

    private int checkYBounds(int pointUnbounded, int transitYPoint) {
        while (mainFrame.getHeight() < pointUnbounded) {
            pointUnbounded -= moveDelta;
            transitYPoint -= moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " mainFrame.getHeight() < pointUnbounded transitYPoint = " + transitYPoint);
        }

        while (oceanHeightDelta + oceanShape.getHeight() > transitYPoint) {
            transitYPoint += moveDelta;
            logger.info("OceanShape exemplar " + oceanShape.getExemplar()
                    + " oceanSize.getMinY() < transitYPoint && transitYPoint < oceanSize.getMinY() = " + transitYPoint);
        }

        return transitYPoint;
    }
}
