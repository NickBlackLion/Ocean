package ua.com.blaclion.frames;

import ua.com.blaclion.classes.Ocean;
import ua.com.blaclion.panels.InfoPanel;
import ua.com.blaclion.panels.OceanPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final int WIDTH = 900;
    private final int HEIGHT = 400;
    private InfoPanel infoPanel;
    private OceanPanel oceanPanel;
    private Ocean ocean;

    public MainFrame() {
        infoPanel = new InfoPanel();
        oceanPanel = new OceanPanel();
        oceanPanel.setFrame(this);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Ocean");
        add(oceanPanel.$$$getRootComponent$$$(), BorderLayout.CENTER);
        add(infoPanel.$$$getRootComponent$$$(), BorderLayout.EAST);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
    }
}
