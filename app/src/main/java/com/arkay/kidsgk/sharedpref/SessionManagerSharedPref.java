package com.arkay.kidsgk.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagerSharedPref {

    private int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "KidsQuiz";
    private static SessionManagerSharedPref instance = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private SessionManagerSharedPref() {
        super();
    }

    public static SessionManagerSharedPref getInstance() {
        if (instance == null) {
            synchronized (SessionManagerSharedPref.class) {
                if (instance == null)
                    instance = new SessionManagerSharedPref();
                else
                    return instance;
            }
        }
        return instance;
    }

    public void setSound(Context context,boolean isSound) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isSound", isSound);
        editor.commit();
    }
    public boolean getSound(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getBoolean("isSound",false);
    }
    public void setMusic(Context context,boolean isMusic) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
        editor.putBoolean("isMusic", isMusic);
        editor.commit();
    }
    public boolean getMusic(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        return sharedPreferences.getBoolean("isMusic",false);
    }


}
