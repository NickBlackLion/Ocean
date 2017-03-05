package ua.com.blaclion.abstract_classes;

import ua.com.blaclion.classes.DrawFish;
import ua.com.blaclion.classes.MoveFish;
import ua.com.blaclion.classes.Ocean;
import ua.com.blaclion.frames.MainFrame;

import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.concurrent.ExecutorService;

//Parent class for all fishes in the ocean
public abstract class Fish extends OceanShape {
    private Rectangle2D oceanShape;
    private java.util.List<DrawFish> drawFishes;
    private Ocean ocean;
    private ExecutorService executor;
    private List<MoveFish> moveFishes;
    private MainFrame mainFrame;
    private int pointYDelta;

    public abstract void swim();

    public abstract void makeNewFish();

    public void setOceanShape(Rectangle2D oceanShape) {
        this.oceanShape = oceanShape;
    }

    public void setDrawFishes(List<DrawFish> drawFishes) {
        this.drawFishes = drawFishes;
    }

    public void setOcean(Ocean ocean) {
        this.ocean = ocean;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public void setMoveFishes(List<MoveFish> moveFishes) {
        this.moveFishes = moveFishes;
    }

    //Get ocean size for positioning fish inside it
    public Rectangle2D getOceanShape() {
        return oceanShape;
    }

    //Get container with prepared fishes for drawing
    public List<DrawFish> getDrawFishes() {
        return drawFishes;
    }

    //Get ocean for repaint particular fish in it
    public Ocean getOcean() {
        return ocean;
    }

    //Get common thread pool for all fishes
    public ExecutorService getExecutor() {
        return executor;
    }

    //Get container with moving fishes
    public List<MoveFish> getMoveFishes() {
        return moveFishes;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public int getPointYDelta() {
        return pointYDelta;
    }

    public void setPointYDelta(int pointYDelta) {
        this.pointYDelta = pointYDelta;
    }
}
