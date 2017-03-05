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
    private List<MoveFish> moveFishes;
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

        if (moveFishes == null){
            moveFishes = new ArrayList<>();
            for (DrawFish drawFish: drawFishes){
                moveFishes.add(new MoveFish(drawFish.getFish(), this));
                drawFish.getFish().setMoveFishes(moveFishes);
            }
        }

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

    public List<MoveFish> getMoveFishes() {
        return moveFishes;
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
        for (int i = 0; i < drawRocks.size(); i++) {
            Rock rock = drawRocks.get(i).getRock();
            firstDrawMethod(rect, rock);

            g2.setColor(drawRocks.get(i).getColor());
            g2.fill(drawRocks.get(i).getRockShape());
            g2.draw(drawRocks.get(i).getRockShape());

            g2.drawString(Integer.toString(drawRocks.get(i).getRock().getExemplar()),
                    (float) drawRocks.get(i).getRock().getXPoint(),
                    (float) drawRocks.get(i).getRock().getYPoint());
        }
    }

    private void drawAlgaeOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawAlgae.size(); i++) {
            Algae algae = drawAlgae.get(i).getAlgae();

            firstDrawMethod(rect, algae);

            g2.setColor(drawAlgae.get(i).getColor());
            g2.fill(drawAlgae.get(i).getAlgaeShape());
            g2.draw(drawAlgae.get(i).getAlgaeShape());

            g2.drawString(Integer.toString(drawAlgae.get(i).getAlgae().getExemplar()),
                    (float) drawAlgae.get(i).getAlgae().getXPoint(),
                    (float) drawAlgae.get(i).getAlgae().getYPoint());
        }
    }

    private void drawFishesOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawFishes.size(); i++) {
            Fish fish = drawFishes.get(i).getFish();
            fish.setOceanShape(rect);
            fish.setOcean(this);
            fish.setMainFrame(mainFrame);
            fish.setPointYDelta(deltaY);

            firstDrawMethod(rect, fish);

            g2.setColor(drawFishes.get(i).getColor());
            g2.fill(drawFishes.get(i).getFishShape());
            g2.draw(drawFishes.get(i).getFishShape());
            g2.drawString(Integer.toString(drawFishes.get(i).getFish().getExemplar()),
                    (float) drawFishes.get(i).getFish().getXPoint(),
                    (float) drawFishes.get(i).getFish().getYPoint());
        }
    }

    private void firstDrawMethod(Rectangle2D rect, OceanShape oceanShape) {
        if (!firstDraw) {
            SetCoordinate startCoordinate = new SetCoordinate(mainFrame, rect, oceanShape, deltaY);
            startCoordinate.setStartX();
            startCoordinate.setStartY();

            Point2D thisObjectPoint = new Point2D.Double(oceanShape.getXPoint(), oceanShape.getYPoint());

            while (pointContainer.isPointNear(thisObjectPoint, thisObjectPoint, oceanShape)) {
                startCoordinate.correctXCoordinate();
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
