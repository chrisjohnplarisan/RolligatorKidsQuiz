package com.arkay.kidsgk.beans;

/**
 * Created by India on 07-04-2017.
 */

public class Leaderboard {

    private String id;
    private String levelNo;
    private int levelStatus;
    private long score=0;
    private String name;
    private String profilePic;
    private int star=0;
    private String countryCode;


    public Leaderboard() {
    }

    public Leaderboard(String id,String levelNo,int levelStatus,long score,int star) {
        this.id = id;
        this.levelNo = levelNo;
        this.levelStatus = levelStatus;
        this.score = score;
        this.star = star;
    }

    public String getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(String levelNo) {
        this.levelNo = levelNo;
    }

    public int getLevelStatus() {
        return levelStatus;
    }

    public void setLevelStatus(int levelStatus) {
        this.levelStatus = levelStatus;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getStar() {
        return star;
    }
}
