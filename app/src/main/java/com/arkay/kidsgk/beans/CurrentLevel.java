package com.arkay.kidsgk.beans;

/**
 * Created by arkayapps on 27/02/17.
 */

public class CurrentLevel {



    private int levelNo;
    private long levelScore;
    private int levelStatus;
    private int levelPlayStatus;
    private int levelType;
    private String levelSubTypeName;
    private long levelStartingScore;
    private int levelRate = 3;
    private int howManyTimelay = 0;
    private int tmpLevelGemsAchieve = 0;


    public CurrentLevel() {

    }

    public CurrentLevel(int levelNo, int levelType, long levelScore, int howManyTimelay) {
        this.levelType = levelType;
        this.levelNo = levelNo;
        this.levelScore = levelScore;
        this.levelStartingScore = levelScore;
        this.howManyTimelay = howManyTimelay;

    }

    public CurrentLevel(int levelNo, int levelType, long levelScore, int howManyTimelay, String levelSubTypeName) {
        this.levelType = levelType;
        this.levelNo = levelNo;
        this.levelScore = levelScore;
        this.howManyTimelay = howManyTimelay;
        this.levelSubTypeName = levelSubTypeName;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }


    public long getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(long levelScore) {
        this.levelScore = levelScore;
    }

    public int getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(int levelStatus) {
        this.levelStatus = levelStatus;
    }

    public int getLevelType() {
        return levelType;
    }

    public void setLevelType(int levelType) {
        this.levelType = levelType;
    }

    public long getLevelStartingScore() {
        return levelStartingScore;
    }

    public void setLevelStartingScore(long levelStartingScore) {
        this.levelStartingScore = levelStartingScore;
    }

    public int getLevelRate() {
        return levelRate;
    }

    public void setLevelRate(int levelRate) {
        this.levelRate = levelRate;
    }

    public int getHowManyTimelay() {
        return howManyTimelay;
    }

    public void setHowManyTimelay(int howManyTimelay) {
        this.howManyTimelay = howManyTimelay;
    }

    public int getTmpLevelGemsAchieve() {
        return tmpLevelGemsAchieve;
    }

    public void setTmpLevelGemsAchieve(int tmpLevelGemsAchieve) {
        this.tmpLevelGemsAchieve = tmpLevelGemsAchieve;
    }

    public String getLevelSubTypeName() {
        return levelSubTypeName;
    }

    public void setLevelSubTypeName(String levelSubTypeName) {
        this.levelSubTypeName = levelSubTypeName;
    }

    public int getLevelPlayStatus() {
        return levelPlayStatus;
    }

    public void setLevelPlayStatus(int levelPlayStatus) {
        this.levelPlayStatus = levelPlayStatus;
    }

    @Override
    public String toString() {
        return "CurrentLevel{" +
                "levelNo=" + levelNo +
                ", levelScore=" + levelScore +
                ", levelStatus=" + levelStatus +
                ", levelType=" + levelType +
                '}';
    }
}
