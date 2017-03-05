package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.*;

public class Rock extends OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color rockColor;

    public Rock() {
        rockColor = Color.gray;
        setWidth(70);
        setHeight(50);

        logger.info("Rock created " + getExemplar());
    }

    @Override
    public Color getColor() {
        return rockColor;
    }
}
