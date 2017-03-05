package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

import java.awt.*;

public class Algae extends OceanShape {
    private Logger logger = Logger.getLogger(this.getClass());
    private Color algaeColor;

    public Algae() {
        algaeColor = Color.green;
        setWidth(2);
        setHeight(80);

        logger.info("Algae created " + getExemplar());
    }

    @Override
    public Color getColor() {
        return algaeColor;
    }
}
