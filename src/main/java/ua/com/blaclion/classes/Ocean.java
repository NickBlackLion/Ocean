package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.frames.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Ocean extends JComponent {
    private int mainPanelWidth;
    private int mainPanelHeight;
    private Logger logger = Logger.getLogger(this.getClass());
    private MainFrame mainFrame;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if(mainFrame != null) {
            mainPanelWidth = mainFrame.getWidth();
            mainPanelHeight = mainFrame.getHeight();
        }

        double pointY = 70;

        Rectangle2D rect = new Rectangle2D.Double(0, pointY, mainPanelWidth, mainPanelHeight - pointY);

        g2.setColor(new Color(15, 150, 233));
        g2.fill(rect);
        g2.draw(rect);

        logger.info(rect);

        super.paintComponents(g2);
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
