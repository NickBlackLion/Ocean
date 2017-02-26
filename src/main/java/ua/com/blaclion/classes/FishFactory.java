package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

/**
 * Created by nick on 24.02.17.
 */
public class FishFactory {
    private Logger logger = Logger.getLogger(this.getClass());

    public Fish getNewFish(Class<?> fishClass){
        Fish insFish = null;

        if (fishClass == Fish.class){
            insFish = new Fish();
        }
        if (fishClass == PredatorFish.class){
            insFish = new PredatorFish();
        }

        return insFish;
    }
}
