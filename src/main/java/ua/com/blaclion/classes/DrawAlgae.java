package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Class that create algae shape
 */
public class DrawAlgae {
    private Algae algae;
    private Rectangle2D algaeShape;
    private Logger logger = Logger.getLogger(this.getClass());

    public DrawAlgae(Algae algae) {
        logger.info("Create DrawAlgae");
        this.algae = algae;
        algaeShape = new Rectangle2D.Double();
    }

    public Rectangle2D getAlgaeShape() {
        algaeShape.setFrame(algae.getXPoint(), algae.getYPoint(), algae.getWidth(), algae.getHeight());
        return algaeShape;
    }

    public Color getColor() {
        return algae.getColor();
    }

    public Algae getAlgae() {
        return algae;
    }
}
