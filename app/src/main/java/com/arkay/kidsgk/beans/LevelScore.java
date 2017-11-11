package com.arkay.kidsgk.beans;

/**
 * Created by arkayapps on 27/02/17.
 */

public class LevelScore {


    public static final int LEVEL_STATUS_COMPLETED = 1; // when level Play and Completed
    public static final int LEVEL_STATUS_UN_COMPLETED = 2; // when level Play and Uncompleted

    private int levelID;
    private long levelScore;
    private int levelRate;
    private int howManyTimePlay;
    private boolean isLevelLock = false;
    private int levelStaus;

    public LevelScore() {

    }

    public LevelScore(int levelID, long levelScore) {
        this.levelID = levelID;
        this.levelScore = levelScore;
    }

    public long getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(long levelScore) {
        this.levelScore = levelScore;
    }


    public int getLevelID() {
        return levelID;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }


    public int getLevelRate() {
        return levelRate;
    }

    public void setLevelRate(int levelRate) {
        this.levelRate = levelRate;
    }

    public int getHowManyTimePlay() {
        return howManyTimePlay;
    }

    public void setHowManyTimePlay(int howManyTimePlay) {
        this.howManyTimePlay = howManyTimePlay;
    }

    public int getLevelStaus() {
        return levelStaus;
    }

    public void setLevelStaus(int levelStaus) {
        this.levelStaus = levelStaus;
    }

    public boolean isLevelLock() {
        return isLevelLock;
    }

    public void setLevelLock(boolean levelLock) {
        isLevelLock = levelLock;
    }

}
