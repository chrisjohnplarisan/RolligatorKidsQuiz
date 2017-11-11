package com.arkay.kidsgk.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkay.kidsgk.R;
import com.arkay.kidsgk.handler.DatabaseHelper;
import com.arkay.kidsgk.utils.Prefs;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Ishan4452 on 5/12/2015.
 */

public class MainApplication extends Application {
    public static final String TAG = MainApplication.class
            .getSimpleName();


    public static DatabaseHelper databaseHelper;

    private Prefs prefs;
    private MediaPlayer rightAnswareSound, wrongeAnswareSound, coinPurchaseSound, scoreCounterSound, gameStartCoundDownSound
            ,gameCoinDropSound, gameStarCollectSound1,gameStarCollectSound2,gameStarCollectSound3, gameLevelSuccessSound,gameLevelFailSound;

    // private GoogleApiHelper googleApiHelper;
   // private Tracker mTracker;

    private RequestQueue mRequestQueue;
    private static MainApplication mInstance;
    private Typeface fontMarkoOneRegular,fontRotbotoRegular;


    public static synchronized MainApplication getInstance() {
        return mInstance;
    }


    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper != null)
            return databaseHelper;

        databaseHelper = new DatabaseHelper(context);
        return databaseHelper;
    }

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     *
     * @return tracker
     */
//    synchronized public Tracker getDefaultTracker() {
//        if (mTracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
//            mTracker = analytics.newTracker(R.xml.global_tracker);
//
//        }
//        return mTracker;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        //firebaseoffline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        getInstance(this);
        mInstance = this;
        prefs = new Prefs(this);
        fontMarkoOneRegular = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "MarkoOne-Regular.ttf");
        fontRotbotoRegular = Typeface.createFromAsset(getApplicationContext().getAssets(),
                "Roboto-Regular.ttf");

        //rightAnsware, wrongeAnsware;
        rightAnswareSound = MediaPlayer.create(getApplicationContext(), R.raw.right_choise);
        wrongeAnswareSound = MediaPlayer.create(getApplicationContext(), R.raw.wrong_choise);
        coinPurchaseSound = MediaPlayer.create(getApplicationContext(), R.raw.game_coin_collect);
        scoreCounterSound = MediaPlayer.create(getApplicationContext(), R.raw.score_counter);
        gameStartCoundDownSound = MediaPlayer.create(getApplicationContext(), R.raw.game_count_down);
        gameCoinDropSound = MediaPlayer.create(getApplicationContext(), R.raw.coin_drop);
        gameStarCollectSound1 = MediaPlayer.create(getApplicationContext(), R.raw.star_collect);
        gameStarCollectSound2 = MediaPlayer.create(getApplicationContext(), R.raw.star_collect);
        gameStarCollectSound3 = MediaPlayer.create(getApplicationContext(), R.raw.star_collect);
        gameLevelSuccessSound = MediaPlayer.create(getApplicationContext(), R.raw.level_success);
        gameLevelFailSound = MediaPlayer.create(getApplicationContext(), R.raw.level_lose);
        // googleApiHelper = new GoogleApiHelper(mInstance);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);

    }

    public int getRequestionSequanceNo() {
        return getRequestQueue().getSequenceNumber();
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public Prefs getPrefs() {
        return prefs;
    }

    public void setPrefs(Prefs prefs) {
        this.prefs = prefs;
    }

    public MediaPlayer getRightAnswareSound() {
        return rightAnswareSound;
    }

    public MediaPlayer getWrongeAnswareSound() {
        return wrongeAnswareSound;
    }

    public MediaPlayer getCoinPurchaseSound() {
        return coinPurchaseSound;
    }

    public MediaPlayer getScoreCounterSound() {
        return scoreCounterSound;
    }

    public MediaPlayer getGameStartCoundDownSound() {
        return gameStartCoundDownSound;
    }

    public MediaPlayer getGameCoinDropSound() {
        return gameCoinDropSound;
    }

    public MediaPlayer getGameStarCollectSound1() {
        return gameStarCollectSound1;
    }

    public MediaPlayer getGameLevelSuccessSound() {
        return gameLevelSuccessSound;
    }

    public MediaPlayer getGameLevelFailSound() {
        return gameLevelFailSound;
    }

    public MediaPlayer getGameStarCollectSound2() {
        return gameStarCollectSound2;
    }

    public MediaPlayer getGameStarCollectSound3() {
        return gameStarCollectSound3;
    }

    public Typeface getFontMarkoOneRegular() {
        return fontMarkoOneRegular;
    }

    public Typeface getFontRotbotoRegular() {
        return fontRotbotoRegular;
    }



}
