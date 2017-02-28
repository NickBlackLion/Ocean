package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DrawFish {
    private GoldFish fish;
    private Ellipse2D fishShape;
    private Logger logger = Logger.getLogger(this.getClass());

    public DrawFish(GoldFish fish) {
        logger.info("Create DrawFish");
        this.fish = fish;
    }

    public Ellipse2D getFishShape() {
        fishShape = new Ellipse2D.Double(fish.getXPoint(),
                fish.getYPoint(),
                fish.getWidth(),
                fish.getHeight()
        );

        return fishShape;
    }

    public Color getColor() {
        return fish.getColor();
    }

    public GoldFish getFish() {
        return fish;
    }
}
