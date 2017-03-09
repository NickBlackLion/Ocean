package ua.com.blaclion.panels;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;
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
    private List<DrawAlgae> drawAlgaes;
    private List<MoveFish> moveFishes;
    private int startAmountOfBarriers = 0;
    private PointsCommonContainer pointsContainer;

    public OceanPanel(MainFrame frame, InfoPanel infoPanel) {
        ocean = new Ocean();
        ocean.setMainFrame(frame);

        int amountOfFishes = (int) 9; //(Math.random() * 15);
        int amountOfRocks = (int) 3; //(Math.random() * 15);
        int amountOfAlgaes = (int) 7; //(Math.random() * 15);
        int amountOfPredators = (int) 4; //(Math.random() * 15);

        pointsContainer = new PointsCommonContainer();
        ocean.setPointContainer(pointsContainer);

        moveFishes = new ArrayList<>();

        drawFishes = new ArrayList<>();
        for (int i = 0; i < amountOfFishes; i++) {
            Fish fish = new FishFactory().getNewFish(GoldFish.class);
            drawFishes.add(new DrawFish(fish));
            fish.setDrawFishes(drawFishes);
            fish.setContainer(pointsContainer);
            fish.setExecutor(infoPanel.getExecutor());
            fish.setMoveFishes(moveFishes);
            moveFishes.add(new MoveFish(fish, ocean));
            Fish.increaseAmountOfFishes();
        }

        for (int i = 0; i < amountOfPredators; i++) {
            Fish fish = new FishFactory().getNewFish(PredatorFish.class);
            drawFishes.add(new DrawFish(fish));
            fish.setDrawFishes(drawFishes);
            fish.setContainer(pointsContainer);
            fish.setExecutor(infoPanel.getExecutor());
            fish.setMoveFishes(moveFishes);
            moveFishes.add(new MoveFish(fish, ocean));
            Fish.increaseAmountOfPredators();
        }

        drawRocks = new ArrayList<>();
        for (int i = 0; i < amountOfRocks; i++) {
            OceanShape oceanBarrier = new BarrierFactory().getNewShape(Rock.class);
            Rock rock = (Rock) oceanBarrier;
            drawRocks.add(new DrawRock(rock));
            rock.setContainer(pointsContainer);
            startAmountOfBarriers++;
        }

        drawAlgaes = new ArrayList<>();
        for (int i = 0; i < amountOfAlgaes; i++) {
            OceanShape oceanBarrier = new BarrierFactory().getNewShape(Algae.class);
            Algae algae = (Algae) oceanBarrier;
            drawAlgaes.add(new DrawAlgae(algae));
            algae.setContainer(pointsContainer);
            startAmountOfBarriers++;
        }

        ocean.setDrawFishes(drawFishes);
        ocean.setDrawRocks(drawRocks);
        ocean.setDrawAlgae(drawAlgaes);

        insPanel.setLayout(new GridLayout(1, 1));
        insPanel.add(ocean);
    }

    public Integer getAmountOfBarriers() {
        return startAmountOfBarriers;
    }

    public List<MoveFish> getMoveFishes() {
        return moveFishes;
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
