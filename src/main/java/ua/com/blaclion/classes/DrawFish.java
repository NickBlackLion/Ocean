package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DrawFish {
    private Fish fish;
    private Ellipse2D fishShape;
    private Logger logger = Logger.getLogger(this.getClass());

    public DrawFish(Fish fish) {
        logger.info("Create DrawFish");
        this.fish = fish;
    }

    public Ellipse2D getFishShape() {

        fishShape = new Ellipse2D.Double(fish.getxPoint(),
                fish.getyPoint(),
                fish.getFishWidth(),
                fish.getFishHeight()
        );

        return fishShape;
    }

    public Color getColor() {
        return fish.getFishColor();
    }

    public Fish getFish() {
        return fish;
    }
}
