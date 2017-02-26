package ua.com.blaclion.classes;

import org.apache.log4j.Logger;

public class CheckFishNear {
    private static Logger logger = Logger.getLogger(CheckFishNear.class);

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
        int InsXMoveCur = currentFish.getxPoint() + xMove;
        int InsYMoveCur = currentFish.getyPoint() + yMove;

        if (InsXMoveCur >= prevFish.getxPoint()
                && InsXMoveCur <= prevFish.getxPoint() + prevFish.getFishWidth()*2
                && InsYMoveCur >= prevFish.getyPoint()
                && InsYMoveCur <= prevFish.getyPoint() + prevFish.getFishHeight()*2) {
            logger.info("First prevFish " + prevFish.getExemplar() + " currentFish " + currentFish.getExemplar());
            return false;
        }

        if(InsXMoveCur + currentFish.getFishWidth()*2 >= prevFish.getxPoint()
                && InsXMoveCur + currentFish.getFishWidth()*2 <= prevFish.getxPoint() + prevFish.getFishWidth()*2
                && InsYMoveCur >= prevFish.getyPoint()
                && InsYMoveCur <= prevFish.getyPoint() + prevFish.getFishHeight()*2){
            logger.info("Second prevFish " + prevFish.getExemplar() + " currentFish " + currentFish.getExemplar());
            return false;
        }

        if(InsXMoveCur >= prevFish.getxPoint()
                && InsXMoveCur <= prevFish.getxPoint() + prevFish.getFishWidth()*2
                && InsYMoveCur >= prevFish.getyPoint()
                && InsYMoveCur <= prevFish.getyPoint() + prevFish.getFishHeight()*2){
            logger.info("Third prevFish " + prevFish.getExemplar() + " currentFish " + currentFish.getExemplar());
            return false;
        }

        if(InsXMoveCur >= prevFish.getxPoint()
                && InsXMoveCur <= prevFish.getxPoint() + prevFish.getFishWidth()
                && InsYMoveCur + currentFish.getFishHeight() >= prevFish.getyPoint()
                && InsYMoveCur <= prevFish.getyPoint() + prevFish.getFishHeight()){
            logger.info("Fourth prevFish " + prevFish.getExemplar() + " currentFish " + currentFish.getExemplar());
            return false;
        }

        return true;
    }
}