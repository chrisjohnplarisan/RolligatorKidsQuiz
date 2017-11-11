package com.arkay.kidsgk.beans;

import java.util.ArrayList;

/**
 * Created by INDIA on 08-02-2017.
 */

public class Question {

    private int question_id;
    private String question_text;
    private String question_image;
    private ArrayList<String> options = new ArrayList<String>();
    private int level_type;
    private int question_type;
    private int question_level_id;
    private String right_ans;
    private String question_explaination;
    private String solution;
    private int is_solution_image;

    public int getIs_solution_image() {
        return is_solution_image;
    }

    public void setIs_solution_image(int is_solution_image) {
        this.is_solution_image = is_solution_image;
    }

    public Question(int question_id, String right_ans) {
        this.question_id = question_id;
        this.right_ans = right_ans;
    }

    public boolean addOption(String option) {
        return this.options.add(option);
    }


    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }


    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public String getQuestion_text() {
        return question_text;
    }

    public void setQuestion_text(String question_text) {
        this.question_text = question_text;
    }

    public String getQuestion_image() {
        return question_image;
    }

    public void setQuestion_image(String question_image) {
        this.question_image = question_image;
    }

    public int getLevel_type() {
        return level_type;
    }

    public void setLevel_type(int level_type) {
        this.level_type = level_type;
    }

    public int getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(int question_type) {
        this.question_type = question_type;
    }

    public int getQuestion_level_id() {
        return question_level_id;
    }

    public void setQuestion_level_id(int question_level_id) {
        this.question_level_id = question_level_id;
    }

    public String getRight_ans() {
        return right_ans;
    }

    public void setRight_ans(String right_ans) {
        this.right_ans = right_ans;
    }

    public String getQuestion_explaination() {
        return question_explaination;
    }

    public void setQuestion_explaination(String question_explaination) {
        this.question_explaination = question_explaination;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
