package com.arkay.kidsgk.beans;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by INDIA on 22-02-2017.
 */

public class GameScore {

    private long totalScore = 0;
    private int countHowManyTimePlay = 0;

    private ArrayList<LevelScore> levelScores = new ArrayList<LevelScore>();


    public GameScore() {

    }

    public GameScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(long totalScore) {
        this.totalScore = totalScore;
    }

    public ArrayList<LevelScore> getLevelScores() {
        return this.levelScores;
    }

    public void setLevelScores(ArrayList<LevelScore> levelScores) {
        this.levelScores = levelScores;
    }

    public void addLevelScore(LevelScore tmpLevelScore) {
        this.levelScores.add(tmpLevelScore);
    }


    public int getCountHowManyTimePlay() {
        return countHowManyTimePlay;
    }

    public void setCountHowManyTimePlay(int countHowManyTimePlay) {
        this.countHowManyTimePlay = countHowManyTimePlay;
    }

    public int getTotalStar() {
        int tempCountStart = 0;

        Log.i("INFO",""+levelScores.size());
        for (LevelScore levelScore : levelScores) {
            Log.i("INFO",""+levelScore);
            tempCountStart = tempCountStart + levelScore.getLevelRate();
        }
        return tempCountStart;
    }
}
