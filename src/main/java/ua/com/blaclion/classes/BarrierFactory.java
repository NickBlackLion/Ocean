package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.OceanShape;

/**
 * Factory for creating barriers
 */
public class BarrierFactory {
    private static Logger logger = Logger.getLogger(BarrierFactory.class);

    public static OceanShape getNewShape(Class<?> barrierClass){
        OceanShape insBarrier = null;

        if (barrierClass == Rock.class){
            insBarrier = new Rock();
        }
        if (barrierClass == Algae.class){
            insBarrier = new Algae();
        }

        return insBarrier;
    }
}
