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

        drawFishesOnPanel(rect, g2);

        if (moveFishes == null){
            moveFishes = new ArrayList<>();
            for (DrawFish drawFish: drawFishes){
                moveFishes.add(new MoveFish(drawFish.getFish(), this));
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

    public List<MoveFish> getMoveFishes() {
        return moveFishes;
    }

    public void setPointContainer(PointsCommonContainer pointContainer) {
        this.pointContainer = pointContainer;
    }

    private void setPointToContainer(int pointX, int pointY, OceanShape oceanShape) {
        Point2D objectPoint = new Point2D.Double(pointX, pointY);
        pointContainer.setPoint(oceanShape.getExemplar(), objectPoint);
        pointContainer.setClass(oceanShape.getExemplar(), oceanShape.getClass());
    }

    private void drawRocksOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawRocks.size(); i++) {
            Rock rock = drawRocks.get(i).getRock();

            if (!firstDraw) {
                SetCoordinate startCoordinate = new SetCoordinate(mainFrame, rect, rock, deltaY);
                startCoordinate.setStartX();
                startCoordinate.setStartY();

                ArrayList<Point2D> points = new ArrayList<>(pointContainer.getPoints());
                Point2D thisObjecctPoint = new Point2D.Double(rock.getXPoint(), rock.getYPoint());

                for (Point2D point2D: points) {

                }

                setPointToContainer(rock.getXPoint(), rock.getYPoint(), rock);
            }

            g2.setColor(drawRocks.get(i).getColor());
            g2.fill(drawRocks.get(i).getRockShape());
            g2.draw(drawRocks.get(i).getRockShape());

            g2.drawString(Integer.toString(rock.getExemplar()),
                    (float) drawRocks.get(i).getRock().getXPoint(),
                    (float) drawRocks.get(i).getRock().getYPoint());
        }
    }

    private void drawFishesOnPanel(Rectangle2D rect, Graphics2D g2) {
        for (int i = 0; i < drawFishes.size(); i++) {
            Fish fish = drawFishes.get(i).getFish();

            if (!firstDraw) {
                SetCoordinate startCoordinate = new SetCoordinate(mainFrame, rect, fish, deltaY);
                startCoordinate.setStartX();
                startCoordinate.setStartY();

                setPointToContainer(fish.getXPoint(), fish.getYPoint(), fish);
            }

            g2.setColor(drawFishes.get(i).getColor());
            g2.fill(drawFishes.get(i).getFishShape());
            g2.draw(drawFishes.get(i).getFishShape());
            g2.drawString(Integer.toString(fish.getExemplar()),
                    (float) drawFishes.get(i).getFish().getXPoint(),
                    (float) drawFishes.get(i).getFish().getYPoint());
        }
    }
}
