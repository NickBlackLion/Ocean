package ua.com.blaclion.classes;

import java.util.Random;

public class Fish {
    private int xSize;
    private int ySize;
    private int xPoint;
    private int yPoint;

    public Fish(int oceanWidth, int oceanHeight) {
        Random xStartPoint = new Random(System.currentTimeMillis()*2);
        Random yStartPoint = new Random(System.currentTimeMillis()*5);

        xPoint = xStartPoint.nextInt(oceanWidth);
        yPoint = yStartPoint.nextInt(oceanHeight);
    }

    public void swim(int xDirection, int yDirection){
        xPoint += xDirection;
        yPoint += yDirection;
    }

    public Fish makeNewFish(){
        return new Fish(xPoint + xSize, yPoint + ySize);
    }



    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int xPoint) {
        this.xPoint = xPoint;
    }

    public int getyPoint() {
        return yPoint;
    }

    public void setyPoint(int yPoint) {
        this.yPoint = yPoint;
    }
}
