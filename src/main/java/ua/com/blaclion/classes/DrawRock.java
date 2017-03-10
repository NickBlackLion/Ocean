package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Class that create rock shape
 */
public class DrawRock {
    private Rock rock;
    private Arc2D rockShape;
    private Logger logger = Logger.getLogger(this.getClass());

    public DrawRock(Rock rock) {
        logger.info("Create DrawRock");
        this.rock = rock;
        rockShape = new Arc2D.Double(Arc2D.PIE);
        rockShape.setAngleStart(0);
        rockShape.setAngleExtent(180);
    }

    public Arc2D getRockShape() {
        rockShape.setFrame(rock.getXPoint(), rock.getYPoint(), rock.getWidth(), rock.getHeight());
        return rockShape;
    }

    public Color getColor() {
        return rock.getColor();
    }

    public Rock getRock() {
        return rock;
    }
}
