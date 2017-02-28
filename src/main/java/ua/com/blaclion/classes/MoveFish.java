package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

public class MoveFish implements Runnable {
    private GoldFish fish;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private boolean isRunning = false;

    public MoveFish(GoldFish fish, Ocean ocean) {
        this.fish = fish;
        this.ocean = ocean;
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            fish.swim();
            ocean.repaint();
        }
    }

    public void kill(){
        isRunning = false;
    }

    public void wakeUp(){
        isRunning = true;
    }
}
