package ua.com.blaclion.classes;

import org.apache.log4j.Logger;
import ua.com.blaclion.abstract_classes.Fish;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that moves fish in its own thread
 */
public class MoveFish {
    private SafetyList<Fish> fishes;
    private Ocean ocean;
    private Logger logger = Logger.getLogger(this.getClass());
    private Lock lock;
    private int timeOut;
    private Timer timer;
    private TimerTask timerTask;

    public MoveFish(Ocean ocean) {
        this.ocean = ocean;
        fishes = new SafetyList<>();
        timeOut = 3000;
        lock = new ReentrantLock();
    }

    public void runFishes() {
        createNewTimerAndTask();
    }

    public void stopFishes() {
        timer.cancel();
    }

    public void restartFishesMove() {
        timer.cancel();

        createNewTimerAndTask();
    }

    public void addToMoveList(Fish fish) {
        lock.lock();
        try {
            fishes.add(fish);
        }
        finally {
            lock.unlock();
        }
    }

    public void removeFromMoveList(Fish fish) {
        lock.lock();
        try {
            fishes.remove(fish);
        }
        finally {
            lock.unlock();
        }
    }

    private class MoveFishTask extends TimerTask {
        @Override
        public void run() {
            moveAllFishes();
        }
    }

    private void moveAllFishes() {
        for (int i = 0; i < fishes.size(); i++) {
            Fish fish = fishes.get(i);
            fish.swim();
            if (fish.isKill()) {
                fish.getMoveFish().removeFromMoveList(fish);
            }
        }

        ocean.repaint();
    }

    private void createNewTimerAndTask() {
        timer = new Timer(true);
        timerTask = new MoveFishTask();
        timer.scheduleAtFixedRate(timerTask, 0, timeOut);
    }
}
