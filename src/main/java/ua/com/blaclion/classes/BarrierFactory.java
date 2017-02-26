package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.interfaces.Barrier;

/**
 * Created by nick on 26.02.17.
 */
public class BarrierFactory {
    private Logger logger = Logger.getLogger(this.getClass());

    public Barrier getNewFish(Class<?> barrierClass){
        Barrier insBarrier = null;

        if (barrierClass == Fish.class){
            insBarrier = new Rock();
        }
        if (barrierClass == PredatorFish.class){
            insBarrier = new Algae();
        }

        return insBarrier;
    }
}
