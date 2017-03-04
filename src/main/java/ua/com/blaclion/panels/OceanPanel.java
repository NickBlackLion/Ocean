package ua.com.blaclion.panels;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;
import ua.com.blaclion.classes.*;
import ua.com.blaclion.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class OceanPanel {
    private JPanel mainPanel;
    private JPanel insPanel;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private List<DrawFish> drawFishes;
    private List<DrawRock> drawRocks;
    private int startAmountOfFishes = 0;
    private int startAmountOfPredators = 0;
    private int startAmountOfBarriers = 0;
    private PointsCommonContainer pointsContainer;

    public OceanPanel(MainFrame frame) {
        ocean = new Ocean();
        ocean.setMainFrame(frame);

        int amountOfFishes = (int) 9; //(Math.random() * 15);
        int amountOfBarriers = (int) 3; //(Math.random() * 15);

        pointsContainer = new PointsCommonContainer();
        ocean.setPointContainer(pointsContainer);

        drawFishes = new ArrayList<>();
        for (int i = 0; i < amountOfFishes; i++) {
            drawFishes.add(new DrawFish(new FishFactory().getNewFish(GoldFish.class)));
            GoldFish fish = (GoldFish) drawFishes.get(i).getFish();
            fish.setDrawFishes(drawFishes);
            fish.setContainer(pointsContainer);
            startAmountOfFishes++;
        }

        drawRocks = new ArrayList<>();
        for (int i = 0; i < amountOfBarriers; i++) {
            OceanShape oceanBarrier = new BarrierFactory().getNewShape(Rock.class);
            Rock rock = (Rock) oceanBarrier;
            drawRocks.add(new DrawRock(rock));
            rock.setContainer(pointsContainer);
            startAmountOfBarriers++;
        }

        ocean.setDrawFishes(drawFishes);
        ocean.setDrawRocks(drawRocks);

        insPanel.setLayout(new GridLayout(1, 1));
        insPanel.add(ocean);
    }

    public Ocean getOcean() {
        return ocean;
    }

    public Integer getStartAmountOfFishes() {
        return startAmountOfFishes;
    }

    public Integer getStartAmountOfPredators() {
        return startAmountOfPredators;
    }

    public Integer getAmountOfBarriers() {
        return startAmountOfBarriers;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        insPanel = new JPanel();
        insPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(insPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
