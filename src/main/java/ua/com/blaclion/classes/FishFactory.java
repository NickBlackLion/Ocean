package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

/**
 * Factory for creating fishes and predators
 */
public class FishFactory {
    private Logger logger = Logger.getLogger(this.getClass());

    public static Fish getNewFish(Class<?> fishClass){
        Fish insFish = null;

        if (fishClass == GoldFish.class){
            insFish = new GoldFish();
        }
        if (fishClass == PredatorFish.class){
            insFish = new PredatorFish();
        }

        return insFish;
    }
}
