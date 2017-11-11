package com.arkay.kidsgk.beans;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by INDIA on 01-02-2017.
 */

public class MathsLevel {

    public int id;
    public String title;
    public String img;
    public int level_type;
    public String level_subtype;
    private int levelRating;
    private int howManyTimePlay;
    private long levelScore;
    private boolean isLevelLock = true;
    private int levelStaus;
    private String levelNo,levelName;

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getLevel_type() {
        return level_type;
    }

    public void setLevel_type(int level_type) {
        this.level_type = level_type;
    }

    public String getLevel_subtype() {
        return level_subtype;
    }

    public void setLevel_subtype(String level_subtype) {
        this.level_subtype = level_subtype;
    }


    public MathsLevel(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public MathsLevel(int id) {
        this.id = id;
    }
    public MathsLevel(String levelNo,String levelName) {
        this.levelNo = levelNo;
        this.levelName = levelName;
    }

    public MathsLevel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getLevelScore() {
        return levelScore;
    }

    public void setLevelScore(long levelScore) {
        this.levelScore = levelScore;
    }

    public int getLevelRating() {
        return levelRating;
    }

    public void setLevelRating(int levelRating) {
        this.levelRating = levelRating;
    }

    public int getHowManyTimePlay() {
        return howManyTimePlay;
    }

    public void setHowManyTimePlay(int howManyTimePlay) {
        this.howManyTimePlay = howManyTimePlay;
    }

    public boolean isLevelLock() {
        return isLevelLock;
    }

    public void setLevelLock(boolean levelLock) {
        isLevelLock = levelLock;
    }

    public int getLevelStaus() {
        return levelStaus;
    }

    public void setLevelStaus(int levelStaus) {
        this.levelStaus = levelStaus;
    }


   /* @Override
    public String toString() {
        try {
            JSONObject scoreObject1 = new JSONObject();
            JSONObject obj = new JSONObject();
            obj.put("scoredata", scoreObject1);
            return scoreObject1.toString();
        } catch (JSONException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error converting save data to JSON.", ex);
        }

    }*/

    @Override
    public String toString() {
        return "MathsLevel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", level_type=" + level_type +
                ", level_subtype='" + level_subtype + '\'' +
                ", levelRating=" + levelRating +
                ", howManyTimePlay=" + howManyTimePlay +
                ", levelScore=" + levelScore +
                ", isLevelLock=" + isLevelLock +
                ", levelStaus=" + levelStaus +
                ", levelNo='" + levelNo + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }
}
