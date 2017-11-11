package com.arkay.kidsgk.beans;

import java.util.List;

/**
 * Created by INDIA on 15-02-2017.
 */

public class QuizLevel {

    private int levelNo;
    private int noOfQuestion;
    private int userLevel;

    private List<Question> question;
    private List<MathsLevel> level_no;

    public QuizLevel(int levelNo, int noOfQuestion) {
        super();
        this.levelNo = levelNo;
        this.noOfQuestion = noOfQuestion;
    }

    public List<MathsLevel> getLevel_no() {
        return level_no;
    }

    public void setLevel_no(List<MathsLevel> level_no) {
        this.level_no = level_no;
    }

    public int getLevelNo() {
        return levelNo;
    }

    public void setLevelNo(int levelNo) {
        this.levelNo = levelNo;
    }

    public int getNoOfQuestion() {
        return noOfQuestion;
    }

    public void setNoOfQuestion(int noOfQuestion) {
        this.noOfQuestion = noOfQuestion;
    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
        this.question = question;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
}
