package ua.com.blaclion.classes;

import javax.swing.*;
import java.awt.*;

public class DrawFish extends JComponent {
    private Fish fish;

    public DrawFish(Fish fish) {
        this.fish = fish;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g2);
    }
}
