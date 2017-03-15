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
    private boolean isRunning = false;
    private Lock lock;
    private int timeOut;
    private Timer timer;
    private TimerTask timerTask;
    private boolean restart;

    public MoveFish(Ocean ocean) {
        this.ocean = ocean;
        isRunning = true;
        fishes = new SafetyList<>();
        timeOut = 3000;
        lock = new ReentrantLock();
        restart = false;
    }

    public void runFishes() {
        createNewTimerAndTask(0);
    }

    public void stopFishes() {
        timer.cancel();
    }

    public void restartFishesMove() {
        timer.cancel();

        moveAllFishes();

        createNewTimerAndTask(2000);
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

    private void createNewTimerAndTask(int delay) {
        timer = new Timer(true);
        timerTask = new MoveFishTask();
        timer.scheduleAtFixedRate(timerTask, delay, timeOut);
    }
}
