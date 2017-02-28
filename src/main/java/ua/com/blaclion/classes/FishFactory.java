package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

/**
 * Created by nick on 24.02.17.
 */
public class FishFactory {
    private Logger logger = Logger.getLogger(this.getClass());

    public Fish getNewFish(Class<?> fishClass){
        GoldFish insFish = null;

        if (fishClass == GoldFish.class){
            insFish = new GoldFish();
        }
        if (fishClass == PredatorFish.class){
            insFish = new PredatorFish();
        }

        return insFish;
    }
}
