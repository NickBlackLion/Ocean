package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
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
    private boolean firstDraw = false;

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

        for (int i = 0; i < drawFishes.size(); i++){
            Fish fish = drawFishes.get(i).getFish();

            if (!firstDraw) {
                if (i == 0) {
                    fish = setFishsStartPoint(fish, rect, pointY);
                } else {
                    Fish fishPrev = drawFishes.get(i - 1).getFish();
                    fish = setFishsStartPoint(fish, rect, pointY);

                    fish = CheckFishNear.isFishNear(fishPrev, fish, (int) pointY);
                }
            }

            g2.setColor(drawFishes.get(i).getColor());
            g2.fill(drawFishes.get(i).getFishShape());
            g2.draw(drawFishes.get(i).getFishShape());
            g2.drawString(Integer.toString(i+1),
                    (float) drawFishes.get(i).getFish().getxPoint(),
                    (float) drawFishes.get(i).getFish().getyPoint());
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

    public List<MoveFish> getMoveFishes() {
        return moveFishes;
    }

    private Fish setFishsStartPoint(Fish fish, Rectangle2D rect, double pointY) {
        fish.setOceanShape(rect);
        fish.setxPoint((int) rect.getWidth());
        fish.setyPoint((int) rect.getHeight(), (int) pointY);
        return fish;
    }
}
