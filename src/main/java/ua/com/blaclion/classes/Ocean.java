package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;
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

        double pointY = 70;

        Rectangle2D rect = new Rectangle2D.Double(0, pointY, mainPanelWidth, mainPanelHeight - pointY);

        g2.setColor(new Color(15, 150, 233));
        g2.fill(rect);
        g2.draw(rect);

        for (int i = 0; i < drawRocks.size(); i++) {
            Rock rock = drawRocks.get(i).getRock();

            if (!firstDraw) {
                rock.setOceanSize(rect);
                rock.setXPoint(mainPanelWidth);
                rock.setYPoint(mainPanelHeight, rock.getHeight());

                for (DrawFish drawFish : drawFishes) {
                    if (pointContainer.isEqualsPoint(new Point2D.Double(rock.getXPoint(), rock.getYPoint()),
                            new Point2D.Double(drawFish.getFish().getXPoint(), drawFish.getFish().getYPoint()),
                            rock.getWidth(), rock.getHeight())) {
                        rock.setXPoint(rock.getXPoint() + rock.getWidth());
                    }
                }
            }

            g2.setColor(drawRocks.get(i).getColor());
            g2.fill(drawRocks.get(i).getRockShape());
            g2.draw(drawRocks.get(i).getRockShape());

            g2.drawString(Integer.toString(rock.getExemplar()),
                    (float) drawRocks.get(i).getRock().getXPoint(),
                    (float) drawRocks.get(i).getRock().getYPoint());

            setPointToContainer(rock.getXPoint(), rock.getYPoint(), rock.getExemplar());
        }

        for (int i = 0; i < drawFishes.size(); i++) {
            Fish fish = drawFishes.get(i).getFish();

            if (!firstDraw) {
                if (fish.getClass() == GoldFish.class) {
                    GoldFish fish1 = (GoldFish) fish;
                    fish1.setOceanShape(rect);
                }
                fish.setXPoint((int) rect.getWidth());
                fish.setYPoint((int) rect.getHeight(), (int) pointY);

                for (DrawFish drawFish : drawFishes) {
                    if (pointContainer.isEqualsPoint(new Point2D.Double(fish.getXPoint(), fish.getYPoint()),
                            new Point2D.Double(drawFish.getFish().getXPoint(), drawFish.getFish().getYPoint()),
                            fish.getWidth(), fish.getHeight())) {
                        fish.setXPoint(fish.getXPoint() + fish.getWidth() * 2);
                    }
                }

                setPointToContainer(fish.getXPoint(), fish.getYPoint(), fish.getExemplar());
            }

            g2.setColor(drawFishes.get(i).getColor());
            g2.fill(drawFishes.get(i).getFishShape());
            g2.draw(drawFishes.get(i).getFishShape());
            g2.drawString(Integer.toString(fish.getExemplar()),
                    (float) drawFishes.get(i).getFish().getXPoint(),
                    (float) drawFishes.get(i).getFish().getYPoint());
        }

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

    private void setPointToContainer(int pointX, int pointY, int exemplar) {
        Point2D fishPoint = new Point2D.Double(pointX, pointY);
        pointContainer.setPoint(exemplar, fishPoint);
    }
}
