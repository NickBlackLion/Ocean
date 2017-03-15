package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;
import ua.com.blaclion.abstract_classes.OceanShape;
import ua.com.blaclion.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Common class for drawing ocean and all its components
 */
public class Ocean extends JComponent {
    private int mainPanelWidth;
    private int mainPanelHeight;
    private Logger logger = Logger.getLogger(this.getClass());
    private MainFrame mainFrame;
    private SafetyList<DrawFish> drawFishes;
    private SafetyList<DrawRock> drawRocks;
    private SafetyList<DrawAlgae> drawAlgae;
    private boolean firstDraw = false;
    private PointsCommonContainer pointContainer;
    private int deltaY;
    private Lock lock;

    public Ocean() {
        lock = new ReentrantLock();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if(mainFrame != null) {
            mainPanelWidth = mainFrame.getWidth() - mainFrame.getInfoPanel().$$$getRootComponent$$$().getWidth();
            mainPanelHeight = mainFrame.getHeight();
        }

        deltaY = 70;

        Rectangle2D rect = new Rectangle2D.Double(0, deltaY, mainPanelWidth, mainPanelHeight - deltaY);

        g2.setColor(new Color(15, 150, 233));
        g2.fill(rect);
        g2.draw(rect);

        drawRocksOnPanel(rect, g2);

        drawAlgaeOnPanel(rect, g2);

        drawFishesOnPanel(rect, g2);

        if (!firstDraw) {
            firstDraw = true;
        }

        super.paintComponents(g2);
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setDrawFishes(SafetyList<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    public void setDrawRocks(SafetyList<DrawRock> drawRocks) {
        this.drawRocks = drawRocks;
    }

    public void setDrawAlgae(SafetyList<DrawAlgae> drawAlgae) {
        this.drawAlgae = drawAlgae;
    }

    public void setPointContainer(PointsCommonContainer pointContainer) {
        this.pointContainer = pointContainer;
    }

    private void setPointToContainer(int pointX, int pointY, OceanShape oceanShape) {
        Point2D objectPoint = new Point2D.Double(pointX, pointY);
        pointContainer.setPoint(oceanShape.getExemplar(), objectPoint);
        pointContainer.setObject(oceanShape.getExemplar(), oceanShape);
    }

    /**
     * Method that draw rocks in the ocean
     * @param rect
     * @param g2
     */
    private void drawRocksOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawRocks.size(); i++) {
            DrawRock drawRock = drawRocks.get(i);
            Rock rock = drawRock.getRock();

            firstDrawMethod(rect, rock);

            g2.setColor(drawRock.getColor());
            g2.fill(drawRock.getRockShape());
            g2.draw(drawRock.getRockShape());

            g2.drawString(Integer.toString(drawRock.getRock().getExemplar()),
                    (float) drawRock.getRock().getXPoint(),
                    (float) drawRock.getRock().getYPoint());
        }
    }

    /**
     * Method that draw algae in the ocean
     * @param rect
     * @param g2
     */
    private void drawAlgaeOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (DrawAlgae drawAlgae: drawAlgae) {
            Algae algae = drawAlgae.getAlgae();

            firstDrawMethod(rect, algae);

            g2.setColor(drawAlgae.getColor());
            g2.fill(drawAlgae.getAlgaeShape());
            g2.draw(drawAlgae.getAlgaeShape());

            g2.drawString(Integer.toString(drawAlgae.getAlgae().getExemplar()),
                    (float) drawAlgae.getAlgae().getXPoint(),
                    (float) drawAlgae.getAlgae().getYPoint());
        }
    }

    /**
     * Method that draw fishes and predators in the ocean
     * @param rect
     * @param g2
     */
    private void drawFishesOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawFishes.size(); i++) {
            DrawFish drawFish = drawFishes.get(i);
            Fish fish = drawFish.getFish();
            if (!firstDraw) {
                fish.setOceanSize(rect);
                fish.setOcean(this);
                fish.setMainFrame(mainFrame);
                fish.setPointYDelta(deltaY);
            }

            firstDrawMethod(rect, fish);

            g2.setColor(drawFish.getColor());
            g2.fill(drawFish.getFishShape());
            g2.draw(drawFish.getFishShape());
            g2.drawString(Integer.toString(drawFish.getFish().getExemplar()),
                    (float) drawFish.getFish().getXPoint(),
                    (float) drawFish.getFish().getYPoint());
        }
    }

    /**
     * Method that makes first draw and positioning components in ocean
     * that they are not intersect each other
     * @param rect
     * @param oceanShape
     */
    private void firstDrawMethod(Rectangle2D rect, OceanShape oceanShape) {
        if (!firstDraw) {
            SetCoordinate startCoordinate = new SetCoordinate(mainFrame, rect, oceanShape, deltaY);
            startCoordinate.setStartX();
            startCoordinate.setStartY();

            Point2D thisObjectPoint = new Point2D.Double(oceanShape.getXPoint(), oceanShape.getYPoint());

            while (pointContainer.isSomeObjectNear(thisObjectPoint, oceanShape)) {
                startCoordinate.correctXCoordinate();
                thisObjectPoint.setLocation(oceanShape.getXPoint(), oceanShape.getYPoint());
            }

            while (pointContainer.isSomeObjectNear(thisObjectPoint, oceanShape)) {
                if (oceanShape.getClass() != Rock.class) {
                    startCoordinate.correctYCoordinate();
                    thisObjectPoint.setLocation(oceanShape.getXPoint(), oceanShape.getYPoint());
                }
                thisObjectPoint.setLocation(oceanShape.getXPoint(), oceanShape.getYPoint());
            }

            setPointToContainer(oceanShape.getXPoint(), oceanShape.getYPoint(), oceanShape);
        }
    }
}
