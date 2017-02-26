package ua.com.blaclion.classes;

public class CheckFishNear {
    public static Fish isFishNear(Fish prevFish, Fish currentFish, int deltaY) {
        //Define x point of the new currentFish in relation to previous currentFish
        if (prevFish.getxPoint() <= currentFish.getxPoint()
                && currentFish.getxPoint() <= (prevFish.getxPoint() + prevFish.getFishWidth())) {
            currentFish.setxPoint(currentFish.getxPoint() - currentFish.getFishWidth() * 2);
        }

        if (prevFish.getxPoint() <= (currentFish.getxPoint() + currentFish.getFishWidth())
                && (currentFish.getxPoint() + currentFish.getFishWidth()) <= (prevFish.getxPoint() + prevFish.getFishWidth())) {
            currentFish.setxPoint(currentFish.getxPoint() + currentFish.getFishWidth() * 2);
        }

        //Define y point of the new currentFish in relation to previous currentFish
        if (prevFish.getyPoint() <= currentFish.getyPoint()
                && currentFish.getyPoint() <= (prevFish.getyPoint() + prevFish.getFishHeight())) {
            currentFish.setyPoint(currentFish.getyPoint() + currentFish.getFishWidth() * 2, deltaY);
        }

        if (prevFish.getyPoint() <= (currentFish.getyPoint() + currentFish.getFishHeight())
                && (currentFish.getyPoint() + currentFish.getFishHeight()) <= (prevFish.getxPoint() + prevFish.getFishHeight())) {
            currentFish.setyPoint(currentFish.getyPoint() - currentFish.getFishHeight() * 2, deltaY);
        }

        return currentFish;
    }

    public static boolean isCanMove(Fish prevFish, Fish currentFish, int xMove, int yMove) {
        int InsXMove = currentFish.getxPoint() + xMove;
        int InsYMove = currentFish.getyPoint() + yMove;

        //Define x point of the new currentFish in relation to previous currentFish
        if (prevFish.getxPoint() <= InsXMove
                && InsXMove <= (prevFish.getxPoint() + prevFish.getFishWidth())
                && prevFish.getyPoint() <= InsYMove
                && InsYMove <= (prevFish.getyPoint() + prevFish.getFishHeight())) {
            return false;
        }

        //Define y point of the new currentFish in relation to previous currentFish
        if (prevFish.getxPoint() <= (InsXMove + currentFish.getFishWidth())
                && (InsXMove + currentFish.getFishWidth()) <= (prevFish.getxPoint() + prevFish.getFishWidth())
                && prevFish.getyPoint() <= (InsYMove + currentFish.getFishHeight())
                && (InsYMove + currentFish.getFishHeight()) <= (prevFish.getxPoint() + prevFish.getFishHeight())) {
            return false;
        }

        return true;
    }
}