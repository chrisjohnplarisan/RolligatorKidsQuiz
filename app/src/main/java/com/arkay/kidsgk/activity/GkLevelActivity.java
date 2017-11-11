/*
package com.arkay.kidsgk.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.adapter.GkLevelAdpter;
import com.arkay.kidsgk.application.MainApplication;
import com.arkay.kidsgk.beans.CurrentLevel;
import com.arkay.kidsgk.beans.GameScore;
import com.arkay.kidsgk.beans.LevelScore;
import com.arkay.kidsgk.beans.MathsLevel;
import com.arkay.kidsgk.handler.QuestionDAO;
import com.arkay.kidsgk.utils.Prefs;
import com.etsy.android.grid.StaggeredGridView;

import java.util.ArrayList;

*/
/**
 * Created by apple on 20/09/17.
 *//*


public class GkLevelActivity extends AppCompatActivity {

    public static final String TAG = LevelActivity.class.getSimpleName();
    public TextView txtTitle;


    FragmentTransaction ft;
    private String uid;

    private GameScore gameScore = new GameScore();
    private CurrentLevel currentLevelInfo = new CurrentLevel();
    boolean isFirsttime = true;
    private Prefs prefs;

    private boolean isAdsShouldDipsly = false;
    //private InterstitialAd interstitialAd;


    private StaggeredGridView staggeredGridView;
    private GkLevelAdpter mathsLevelAdapter;
    private ArrayList<MathsLevel> mathsLevels;
    //private View view;
    //public TextView txt;
    private Typeface tp;

    private int selectedIndex = 0;
    Dialog pauseDialog, confirmDialog;
    int level_type=0;
    Context mContext;// = this;
    private Typeface tpMarkOne;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.gk_level_fragment);

        // loadRewardedVideoAd(); // uncomment before publish

        mContext = this;

        tpMarkOne = MainApplication.getInstance().getFontMarkoOneRegular();
        prefs = MainApplication.getInstance().getPrefs();


        //toolbar.setTitle("")


        prefs.setFromGameOver(false);
        displayGrid();
        //uid = prefs.getUID();

    }


    private void displayGrid(){


        //Log.i("INFO","Play Quiz Time: "+mListener.getHowManyTimePlayQuiz());

        //Log.i("INFO",""+gameScore.getCountHowManyTimePlay());


        staggeredGridView = (StaggeredGridView) findViewById(R.id.gridView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);

        txtTitle.setTypeface(tpMarkOne);
        // QuestionDAO mainTableDAO = new QuestionDAO(this);
        // mathsLevels = mainTableDAO.getLevelInfo();
        mathsLevels = new ArrayList<>();
        mathsLevels.add(new MathsLevel(1));
        mathsLevels.add(new MathsLevel(2));
        mathsLevels.add(new MathsLevel(3));
        mathsLevels.add(new MathsLevel(4));
        mathsLevels.add(new MathsLevel(5));
        Log.i("mathsLevels",""+mathsLevels);


        //Log.i("INFO", "Level: " + mListener.getLevelGameScore().size());
        unlockLevel(1);

        for (LevelScore tmpLevelScore : getLevelGameScore()) {
            for (MathsLevel tmpMaths : mathsLevels) {
                if (tmpMaths.getId() == tmpLevelScore.getLevelID()) {
                    tmpMaths.setLevelScore(tmpLevelScore.getLevelScore());
                    tmpMaths.setLevelRating(tmpLevelScore.getLevelRate());
                    tmpMaths.setHowManyTimePlay(tmpLevelScore.getHowManyTimePlay());
                    tmpMaths.setLevelLock(tmpLevelScore.isLevelLock());
                    tmpMaths.setLevelStaus(tmpLevelScore.getLevelStaus());
                    //Log.i("INFO", "ID: " + tmpLevelScore.getLevelID() + " : " + tmpLevelScore.getLevelStaus());
                }
            }
        }
        mathsLevelAdapter = new GkLevelAdpter(this, mathsLevels);
        staggeredGridView.setAdapter(mathsLevelAdapter);

        staggeredGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {

                //Log.i("INFO", "This Level: " + mathsLevels.get(position).isLevelLock());

                selectedIndex = position;
                playLevel();
                //Log.i("INFO", "Select BY Level: " + selectedIndex);
                prefs.setLastLevelSelected(selectedIndex);

            }
        });
    }

    private void playLevel(){
        MathsLevel mathsLevel = mathsLevelAdapter.getItem(selectedIndex);

        level_type = mathsLevel.getLevel_type();
        String level_typename = mathsLevel.getLevel_subtype();

        // Log.i("INFO","Fragment Count: "+getSupportFragmentManager().getBackStackEntryCount());
        int backStackCount = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            // Get the back stack fragment id.
            int backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getId();
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
        //Log.i("INFO","Level Type: "+level_type);

//        if (level_type == LEVEL_TYPE_TRUE_FALSE) {
//            clickTrueFalse(new CurrentLevel(mathsLevel.getId(), LEVEL_TYPE_TRUE_FALSE, mathsLevel.getLevelScore(), mathsLevel.getHowManyTimePlay()));
//        } else if (level_type == LEVEL_TYPE_QUICK_MATHS) {
        clickOnPlayQuiz(new CurrentLevel(mathsLevel.getId(), 1, mathsLevel.getLevelScore(), mathsLevel.getHowManyTimePlay()));
//        } else if (level_type == LEVEL_TYPE_APTITIDE) {
//            clickonAptitude(new CurrentLevel(mathsLevel.getId(), LEVEL_TYPE_APTITIDE, mathsLevel.getLevelScore(), mathsLevel.getHowManyTimePlay(), level_typename));
//        } else if (level_type == LEVEL_TYPE_LOGICAL) {
//            clickonAptitude(new CurrentLevel(mathsLevel.getId(), LEVEL_TYPE_LOGICAL, mathsLevel.getLevelScore(), mathsLevel.getHowManyTimePlay(), level_typename));
//        }
    }


    public void loadInterstitialAd() {
        // interstitialAd = new InterstitialAd(this, "755300331269509_1058619187604287");
//        isAdsShouldDipsly = true;
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
//        AdRequest adRequest;
//        if(getResources().getBoolean(R.bool.isTestMode)){
//            adRequest = new AdRequest.Builder()
//                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                    .addTestDevice("0C2DF43E6E70766851B6A3E5EE46A9B8")
//                    .addTestDevice("E10CFA7B6C484AD18A1356C3E659CC09")
//                    .addTestDevice("54BDDE3D2B6D19827B248558A4971B35")
//                    .addTestDevice("AFE866BB9099AEDD026BF576D2EB4889")
//                    .addTestDevice("B81C8CF2B009D919B43B69D0FEC57EE2")
//                    .addTestDevice("B81C8CF2B009D919B43B69D0FEC57EE2")
//                    .addTestDevice("D4F9CC518EADF120DDA2EFF49390C315")
//                    .addTestDevice("A815D443EF9A92B3EF35A025747A4F16")
//
//                    .build();
//
//        }else{
//            adRequest = new AdRequest.Builder().build();
//        }
//        mInterstitialAd.loadAd(adRequest);
    }
    private void clickTrueFalse(CurrentLevel levelInfo){
        currentLevelInfo = levelInfo;
        //Log.i("INFO", "Rate: " + levelInfo.getLevelRate());

        Bundle bundle = new Bundle();
//        fragmentQuickMaths = FragmentQuickMaths.newInstance(bundle);
//        fragmentQuickMaths.setListener(this);
//        bundle.putBoolean("isquickmaths", false);
//        fragmentQuickMaths.setArguments(bundle);
//
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
//
//        ft.replace(R.id.ha_play_flContentContainer1, fragmentQuickMaths).addToBackStack(null).commit();
        //ft.replace(R.id.ha_play_flContentContainer1, fragmentQuickMaths).addToBackStack(null).commit();



    }

    @Override
    public void onResume() {
        //mAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        //mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // mAd.destroy(this);
        super.onDestroy();
    }



    public void clickOnPlayQuiz(CurrentLevel levelInfo) {

        currentLevelInfo = levelInfo;
        // Log.i("INFO", "Rate: " + levelInfo.getLevelRate());

        Bundle bundle = new Bundle();
//        fragmentQuickMaths = FragmentQuickMaths.newInstance(bundle);
//        fragmentQuickMaths.setListener(this);
//        bundle.putBoolean("isquickmaths", true);
//        fragmentQuickMaths.setArguments(bundle);
//
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
//
//        ft.replace(R.id.ha_play_flContentContainer1, fragmentQuickMaths).addToBackStack(null).commit();


    }


//    public void clickonAptitude(CurrentLevel levelInfo) {
//
//        //Log.i("INFO","Call Aptitude");
//
//        saveCurrentLevel(levelInfo);
//
//        Bundle bundle = new Bundle();
//        fragmentAptitude = FragmentAptitude.newInstance(bundle);
//        fragmentAptitude.setListener(this);
//
////        ft = getSupportFragmentManager().beginTransaction();
////        ft.setCustomAnimations(R.anim.slide_bottom_enter,
////                R.anim.slide_bottom_exit,
////                R.anim.slide_top_enter,
////                R.anim.slide_top_exit);
////        ft.replace(R.id.ha_play_flContentContainer1, fragmentAptitude);
////        ft.commit();
//
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
//        ft.replace(R.id.ha_play_flContentContainer1, fragmentAptitude).addToBackStack(null).commit();
//
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }


//    @Override
//    public void displyHomeScreen() {
//        //Log.i("INFO", "Count: " + getSupportFragmentManager().getBackStackEntryCount());
//        finish();
//    }


//    @Override
//    public void playAgain() {
//        // int count = getSupportFragmentManager().getBackStackEntryCount();
//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        getSupportFragmentManager().popBackStackImmediate();
//
//        // getSupportFragmentManager().popBackStackImmediate();
//        CurrentLevel currentLevelTemp = getCurrentLevel();
//        switch (currentLevelTemp.getLevelType()) {
//            case Constants.LEVEL_TYPE_TRUE_FALSE:
//                clickTrueFalse(new CurrentLevel(currentLevelTemp.getLevelNo(), Constants.LEVEL_TYPE_TRUE_FALSE, currentLevelTemp.getLevelScore(), currentLevelTemp.getHowManyTimelay()));
//                break;
//            case Constants.LEVEL_TYPE_QUICK_MATHS:
//                clickOnPlayQuiz(new CurrentLevel(currentLevelTemp.getLevelNo(), Constants.LEVEL_TYPE_QUICK_MATHS, currentLevelTemp.getLevelScore(), currentLevelTemp.getHowManyTimelay()));
//                break;
//            case Constants.LEVEL_TYPE_LOGICAL:
//                clickonAptitude(new CurrentLevel(currentLevelTemp.getLevelNo(), Constants.LEVEL_TYPE_LOGICAL, currentLevelTemp.getLevelScore(), currentLevelTemp.getHowManyTimelay(),currentLevelTemp.getLevelSubTypeName()));
//                break;
//            case Constants.LEVEL_TYPE_APTITIDE:
//                clickonAptitude(new CurrentLevel(currentLevelTemp.getLevelNo(), Constants.LEVEL_TYPE_APTITIDE, currentLevelTemp.getLevelScore(), currentLevelTemp.getHowManyTimelay(),currentLevelTemp.getLevelSubTypeName()));
//                break;
//        }
//
//
//    }


    public void playNext() {

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        CurrentLevel currentLevelTemp = new CurrentLevel();

        QuestionDAO mainTableDAO = new QuestionDAO(mContext);
        int tmpLevelNo = currentLevelInfo.getLevelNo();
        //Log.i("IINFO", "Before: Level NO " + tmpLevelNo);

        tmpLevelNo++;
        currentLevelTemp.setLevelNo(tmpLevelNo);
        MathsLevel level = mainTableDAO.getLevelInfoByLevelID(tmpLevelNo);
        currentLevelTemp.setLevelType(level.getLevel_type());
        currentLevelTemp.setLevelRate(0);
        currentLevelTemp.setLevelSubTypeName(level.getLevel_subtype());


        for (LevelScore tmpLevelScore : getLevelGameScore()) {
            //Log.i("INFO", " Maths Level: " + (level.getId() == tmpLevelScore.getLevelID()));
            if (level.getId() == tmpLevelScore.getLevelID()) {
                level.setLevelScore(tmpLevelScore.getLevelScore());
                level.setLevelRating(tmpLevelScore.getLevelRate());
                level.setHowManyTimePlay(tmpLevelScore.getHowManyTimePlay());
            }
        }

        // getSupportFragmentManager().popBackStackImmediate();
        //getSupportFragmentManager().popBackStackImmediate();
        //getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

    }

//    @Override
//    public void displayIntersitialAds() {
//        if(mInterstitialAd!=null) {
//            if (mInterstitialAd.isLoaded()) {
//                mInterstitialAd.show();
//            } else {
//                Log.d("TAG", "The interstitial wasn't loaded yet.");
//            }
//
//        }
//    }

//    @Override
//    public void gameOver() {
//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        Log.i("INFO","Stack: "+getSupportFragmentManager().getBackStackEntryCount());
//        int count = getSupportFragmentManager().getBackStackEntryCount();
//
//        fragmentGameOver = new FragmentGameOver();
//        fragmentGameOver.setListener(this);
//        ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.ha_play_flContentContainer1, fragmentGameOver).addToBackStack(null).commit();
//
//
//    }


    public void saveCurrentLevel(CurrentLevel levelInfo) {
        currentLevelInfo = levelInfo;
    }

    public CurrentLevel getCurrentLevel() {
        return currentLevelInfo;
    }


    public void unlockLevel(int levelNo){
        Log.i("INFO","UnLOck ID: "+levelNo);

        boolean isExitFound = false;
        for (LevelScore tmpLevelS : gameScore.getLevelScores()) {
            if (tmpLevelS.getLevelID() == levelNo) {
                isExitFound = true;
            }
        }

        if (!isExitFound) {
            LevelScore tmpLevelScore = new LevelScore();
            tmpLevelScore.setLevelID(levelNo);
            tmpLevelScore.setLevelScore(0);
            tmpLevelScore.setHowManyTimePlay(0);
            tmpLevelScore.setLevelLock(false);
            tmpLevelScore.setLevelRate(0);

            gameScore.addLevelScore(tmpLevelScore);
            //scoreBoardDataRef.setValue(gameScore);
            //showDialogAndPlay();
        }
        //showDialogAndPlay();
    }

    public void saveScoreInFirebase() {

        long thisLevelActualScoreDiff = currentLevelInfo.getLevelScore();

        //Check Current Level Score Greater then Old Score
        prefs.setLastLevelScore(currentLevelInfo.getLevelScore());
        if (currentLevelInfo.getLevelStartingScore() > currentLevelInfo.getLevelScore()) {
            currentLevelInfo.setLevelScore(currentLevelInfo.getLevelStartingScore());
            thisLevelActualScoreDiff = 0;
        } else {
            thisLevelActualScoreDiff = currentLevelInfo.getLevelScore() - currentLevelInfo.getLevelStartingScore();
        }

        int tempPlayCount = gameScore.getCountHowManyTimePlay();
        tempPlayCount++;
        gameScore.setCountHowManyTimePlay(tempPlayCount);

        for (LevelScore tmpLevelS : gameScore.getLevelScores()) {
            if (tmpLevelS.getLevelID() == getCurrentLevel().getLevelNo()) {

                tmpLevelS.setLevelScore(currentLevelInfo.getLevelScore());
                int tmpHowManyTimePlay = currentLevelInfo.getHowManyTimelay();
                tmpHowManyTimePlay++;
                Log.i("INFO", "How Many Time Play: " + tmpHowManyTimePlay);
                tmpLevelS.setHowManyTimePlay(tmpHowManyTimePlay);

                if(tmpLevelS.getLevelStaus()<currentLevelInfo.getLevelStatus()) {
                    tmpLevelS.setLevelStaus(currentLevelInfo.getLevelStatus());
                }

                if (tmpLevelS.getLevelRate() < currentLevelInfo.getLevelRate()) {
                    if (currentLevelInfo.getLevelStatus() == LevelScore.LEVEL_STATUS_COMPLETED) {
                        tmpLevelS.setLevelRate(currentLevelInfo.getLevelRate());
                    }

                }
                Log.i("INFO", "Total Level Games: " + tmpLevelS.getLevelGemesAchieve());
                if (tmpLevelS.getLevelGemesAchieve() < 5) {
                    if (currentLevelInfo.getLevelStatus() == LevelScore.LEVEL_STATUS_COMPLETED) {
                        int james = gameScore.getTotalGems() + currentLevelInfo.getLevelRate();
                        gameScore.setTotalGems(james);
                        currentLevelInfo.setTmpLevelGemsAchieve(currentLevelInfo.getLevelRate());
                        tmpLevelS.setLevelGemesAchieve(tmpLevelS.getLevelGemesAchieve() + currentLevelInfo.getLevelRate());
                    }
                }

            }
        }

        if (currentLevelInfo.getLevelStatus() == LevelScore.LEVEL_STATUS_COMPLETED) {
            long totalScore = gameScore.getTotalScore() + thisLevelActualScoreDiff;
            Log.i("INFO", "NEW Total Score: " + totalScore + " : " + thisLevelActualScoreDiff);
            gameScore.setTotalScore(totalScore);
        }
        //scoreBoardDataRef.setValue(gameScore);

        if (currentLevelInfo.getLevelStatus() == LevelScore.LEVEL_STATUS_COMPLETED) {
            int unLockLevel = currentLevelInfo.getLevelNo();
            unLockLevel = unLockLevel + 1;
            unlockLevel(unLockLevel);
        }

        // Log.i("INFO", "Levle Gems: " + currentLevelInfo.getTmpLevelGemsAchieve());

//        Leaderboard saveLeaderboard = new Leaderboard();
//        saveLeaderboard.setName(user.getUserDisplayName());
//        saveLeaderboard.setScore(gameScore.getTotalScore());
//        saveLeaderboard.setProfilePic(user.getProfileImage());
//        saveLeaderboard.setCountryCode(user.getCountryCode());
//        saveLeaderboard.setStar(gameScore.getTotalStar());
//        databaseUserLeaderboard.setValue(saveLeaderboard);
    }

    public void expenseGames(int expense) {
        int totalGames = gameScore.getTotalGems();
        Log.i("INFO", "Before Games: " + totalGames);
        if (totalGames > expense) {
            totalGames = totalGames - expense;
            gameScore.setTotalGems(totalGames);
            Log.i("INFO", "NEw Games: " + totalGames);
            //scoreBoardDataRef.setValue(gameScore);
        } else {
        }
    }

    public int getGems() {
        return gameScore.getTotalGems();
    }



    public ArrayList<LevelScore> getLevelGameScore() {

        if (gameScore == null) {
            gameScore = new GameScore();
        }
        return gameScore.getLevelScores();
    }
    public int getHowManyTimePlayQuiz() {

        if (gameScore == null) {
            gameScore = new GameScore();
        }
        return gameScore.getCountHowManyTimePlay();
    }

    @Override
    public void onBackPressed() {
        Log.i("INFO", "Count: " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 2) {

            getSupportFragmentManager().popBackStackImmediate();
            getSupportFragmentManager().popBackStackImmediate();

        } else {
            super.onBackPressed();
        }

        boolean isFromGameOver = prefs.isFromGameOver();
        System.out.println("isfrom over : " + isFromGameOver);
        if (isFromGameOver) {
            // fragmentGameOver.Remove();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


    public long getGameTotalScore() {
        return this.gameScore.getTotalScore();
    }


    public void removePlayFragment(){
        Log.i("INFO","Remove Clal");
        getSupportFragmentManager().popBackStackImmediate();
    }

    public boolean isAdsShouldDipsly() {
        return isAdsShouldDipsly;
    }

    public void setAdsShouldDipsly(boolean adsShouldDipsly) {
        isAdsShouldDipsly = adsShouldDipsly;
    }



}

*/
