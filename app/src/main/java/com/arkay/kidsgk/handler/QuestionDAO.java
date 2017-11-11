package com.arkay.kidsgk.handler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.arkay.kidsgk.application.MainApplication;
import com.arkay.kidsgk.beans.MathsLevel;
import com.arkay.kidsgk.beans.Question;
import com.arkay.kidsgk.beans.QuizLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by INDIA on 08-02-2017.
 */

public class QuestionDAO {

    DatabaseHelper databaseHalper;

    public QuestionDAO(Context context) {
        this.databaseHalper = MainApplication.getInstance(context);
    }

    public void getReasoningQuestion(QuizLevel level, int level_no, int level_type) {

        List<Question> levelQuestion = new ArrayList<Question>();
        levelQuestion = getQuestionRendom(level.getNoOfQuestion(), level_type, level_no);
        Collections.shuffle(levelQuestion);
        level.setQuestion(levelQuestion);
    }


    public List<Question> getQuestionRendom(int noOfQuestion, int level_type, int level_no) {
        List<Question> playQuizquestions = new ArrayList<Question>();
        int total = noOfQuestion + 5;

        String sqlQuery;
        sqlQuery = "select *  FROM " + DatabaseHelper.QUESTIONS_TAB + " WHERE " + DatabaseHelper.QUESTION_LEVEL_TYPE + " = " + level_type + " AND " + DatabaseHelper.QUESTION_LEVEL_ID + " = " + level_no;
        SQLiteDatabase db = databaseHalper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) { // data?
            do {
                Question question = new Question(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_ID)), cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_OPTION_A)));
                question.addOption(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_OPTION_A)));
                question.addOption(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_OPTION_B)));
                question.addOption(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_OPTION_C)));
                question.addOption(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_OPTION_D)));
                question.setQuestion_text(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_TEXT)));
                question.setQuestion_image(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTIONS_IMAGE)));

                question.setLevel_type(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.QUESTION_LEVEL_TYPE)));
                question.setQuestion_type(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.QUESTION_TYPE)));
                question.setQuestion_level_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.QUESTION_LEVEL_ID)));
                question.setSolution(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTION_SOLUTION)));
                question.setQuestion_explaination(cursor.getString(cursor.getColumnIndex(DatabaseHelper.QUESTION_EXPLAINATION)));
                question.setIs_solution_image(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.IS_SOLUTION_IMAGE)));

                if (question.getOptions().size() == 4) {
                    playQuizquestions.add(question);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return playQuizquestions;
    }



    public MathsLevel getLevelInfoByLevelID(int levelID) {
        MathsLevel mathsLevel = new MathsLevel();
        String sqlQuery;
        sqlQuery = "select *  FROM " + DatabaseHelper.LEVEL_TAB + " WHERE " + DatabaseHelper.LEVEL_ID + " = " + levelID;
        SQLiteDatabase db = databaseHalper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) { // data?
            do {
                String questionStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_NAME));

                mathsLevel = new MathsLevel(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_ID)), questionStr);
                mathsLevel.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_ID)));
                mathsLevel.setImg(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_IMAGE)));
                mathsLevel.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_NAME)));
                mathsLevel.setLevel_type(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_TYPE)));
                mathsLevel.setLevel_subtype(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_SUBTYPE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // Log.i("INFO","No of Level: "+mathsLevel.size());
        return mathsLevel;
    }

    public List<MathsLevel> getLevelInfo() {
        List<MathsLevel> playQuizquestions = new ArrayList<MathsLevel>();
        String sqlQuery;
        sqlQuery = "select *  FROM " + DatabaseHelper.LEVEL_TAB;
        //Log.i("INFO", "" + sqlQuery);
        SQLiteDatabase db = databaseHalper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) { // data?
            do {
                String questionStr = cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_NAME));

                MathsLevel question = new MathsLevel(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_ID)), questionStr);
                question.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_ID)));
                question.setImg(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_IMAGE)));
                question.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_NAME)));
                question.setLevel_type(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LEVEL_TYPE)));
                question.setLevel_subtype(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LEVEL_SUBTYPE)));
                playQuizquestions.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        //Log.i("INFO", "No of Level: " + playQuizquestions.size());
        return playQuizquestions;
    }
}
