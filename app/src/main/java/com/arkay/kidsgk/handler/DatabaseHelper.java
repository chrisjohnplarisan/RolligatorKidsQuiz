package com.arkay.kidsgk.handler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Information
    public static final String DATABASE_NAME = "database1.db";
    public static final int DATABASE_VERSION = 5;


    public static final String LEVEL_TAB = "level";
    public static final String LEVEL_ID = "level_id";
    public static final String LEVEL_NAME = "level_name";
    public static final String LEVEL_IMAGE = "level_image_name";
    public static final String LEVEL_TYPE = "level_type";
    public static final String LEVEL_SUBTYPE = "level_subtype";


    public static final String QUESTION_TYPE_TAB = "question_type";
    public static final String QUESTION_TYPE_ID = "question_type_id";
    public static final String QUESTION_TYPE_NAME = "question_type_name";


    public static final String QUESTIONS_TAB = "question";
    public static final String QUESTIONS_ID = "question_id";
    public static final String QUESTIONS_TEXT = "question_text";
    public static final String QUESTIONS_IMAGE = "question_image";
    public static final String QUESTIONS_OPTION_A = "option_a";
    public static final String QUESTIONS_OPTION_B = "option_b";
    public static final String QUESTIONS_OPTION_C = "option_c";
    public static final String QUESTIONS_OPTION_D = "option_d";
    public static final String QUESTION_RIGHT_ANSWER = "right_answer";
    public static final String QUESTION_LEVEL_TYPE = "level_type";
    public static final String QUESTION_TYPE = "question_type";
    public static final String QUESTION_LEVEL_ID = "question_level_id";
    public static final String QUESTION_SOLUTION = "solution";
    public static final String QUESTION_EXPLAINATION = "question_explaination";
    public static final String IS_SOLUTION_IMAGE = "is_solution_image";

    public DatabaseHelper(Context contex) {
        super(contex, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuestionTable = "CREATE TABLE IF NOT EXISTS " + QUESTIONS_TAB
                + "( "
                + QUESTIONS_ID + " INTEGER PRIMARY KEY,"
                + QUESTIONS_TEXT + " TEXT,"
                + QUESTIONS_IMAGE + " TEXT,"
                + QUESTIONS_OPTION_A + " TEXT,"
                + QUESTIONS_OPTION_B + " TEXT,"
                + QUESTIONS_OPTION_C + " TEXT,"
                + QUESTIONS_OPTION_D + " TEXT,"
                + QUESTION_RIGHT_ANSWER + " INTEGER,"
                + QUESTION_LEVEL_TYPE + " INTEGER,"
                + QUESTION_TYPE + " INTEGER,"
                + QUESTION_LEVEL_ID + " INTEGER,"
                + QUESTION_SOLUTION + " TEXT,"
                + IS_SOLUTION_IMAGE + " TEXT,"
                + QUESTION_EXPLAINATION + " TEXT)";


        String createQuestionTypeTable = "CREATE TABLE IF NOT EXISTS " + QUESTION_TYPE_TAB
                + "( "
                + QUESTION_TYPE_ID + " INTEGER PRIMARY KEY,"
                + QUESTION_TYPE_NAME + " TEXT)";


        String createMainLevelLeveTable = "CREATE TABLE IF NOT EXISTS " + LEVEL_TAB +
                "( " + LEVEL_ID + " INTEGER primary key,"
                + LEVEL_NAME + " text, "
                + LEVEL_TYPE + " INTEGER, "
                + LEVEL_SUBTYPE + " text, "
                + LEVEL_IMAGE + " text )";


        db.execSQL(createQuestionTypeTable);
        db.execSQL(createMainLevelLeveTable);
        db.execSQL(createQuestionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        onCreate(db);
    }
}
