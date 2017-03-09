package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;
import ua.com.blaclion.abstract_classes.OceanShape;
import ua.com.blaclion.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

public class Ocean extends JComponent {
    private int mainPanelWidth;
    private int mainPanelHeight;
    private Logger logger = Logger.getLogger(this.getClass());
    private MainFrame mainFrame;
    private List<DrawFish> drawFishes;
    private List<DrawRock> drawRocks;
    private List<DrawAlgae> drawAlgae;
    private boolean firstDraw = false;
    private PointsCommonContainer pointContainer;
    private int deltaY;

    public Ocean() {
        drawFishes = new ArrayList<>();
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

    public void setDrawFishes(java.util.List<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    public void setDrawRocks(List<DrawRock> drawRocks) {
        this.drawRocks = drawRocks;
    }

    public void setDrawAlgae(List<DrawAlgae> drawAlgae) {
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

    private void drawRocksOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (DrawRock drawRock: drawRocks) {
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

    private void drawFishesOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (DrawFish drawFish: drawFishes) {
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

    private void firstDrawMethod(Rectangle2D rect, OceanShape oceanShape) {
        if (!firstDraw) {
            SetCoordinate startCoordinate = new SetCoordinate(mainFrame, rect, oceanShape, deltaY);
            startCoordinate.setStartX();
            startCoordinate.setStartY();

            Point2D thisObjectPoint = new Point2D.Double(oceanShape.getXPoint(), oceanShape.getYPoint());

            while (pointContainer.isPointNear(thisObjectPoint, oceanShape)) {
                startCoordinate.correctXCoordinate();
                thisObjectPoint.setLocation(oceanShape.getXPoint(), oceanShape.getYPoint());
            }

            while (pointContainer.isPointNear(thisObjectPoint, oceanShape)) {
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
