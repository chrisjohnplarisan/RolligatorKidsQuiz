package com.arkay.kidsgk.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.beans.Leaderboard;
import com.arkay.kidsgk.beans.QuestionData;
import com.arkay.kidsgk.fragment.FragmentGameOver;
import com.arkay.kidsgk.sharedpref.SessionManagerSharedPref;
import com.arkay.kidsgk.utils.Prefs;
import com.arkay.kidsgk.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayQuizActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = PlayQuizActivity.class.getSimpleName();
    private DatabaseReference questionDatabaseReference;

    // private TextView opA,opB,opC,opD;
    private TextView questionName, rightQuestion, wrongQuestion, totalQuestion, totalScoretxt,levelNameTxt;
    //private Button opAImg,opBImg,opCImg,opDImg;
    private Button opA, opB, opC, opD;
    private ImageView backImg;
    private List<QuestionData> quesList;
    private QuestionData currentQ;
    private int qid = 0, wrongCount = 0, rightCount = 0, totalScore = 0, gemsCount;
    private MediaPlayer mediaPlayer, backMusic;
    private FragmentGameOver fragmentGameOver;
    SessionManagerSharedPref sharedPref = SessionManagerSharedPref.getInstance();

    // private static final String TAG = "AnonymousAuth";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId,levelNo="";
    private long firebaseTotalScore;
    private long firebaseLastScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_quiz);
        init();

        levelNo=getIntent().getStringExtra("levelno");
        levelNameTxt.setText(getResources().getString(R.string.play_quiz)+" : "+levelNo);
        questionDatabaseReference = FirebaseDatabase.getInstance().getReference(Prefs.FB_QUESTION).child(getIntent().getStringExtra("levelno"));
        questionDatabaseReference.keepSynced(true);
//        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(Prefs.FB_LEADERBOARD).child(levelNo);
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(Prefs.FB_LEADERBOARD);
        getLeaderboardDataFromFirebase();
        opA.setOnClickListener(this);
        opB.setOnClickListener(this);
        opC.setOnClickListener(this);
        opD.setOnClickListener(this);
        backImg.setOnClickListener(this);
        getQuestionData();
        setBackgroundSound();

    }

    private void setQuestion() {
        setEnable();
        currentQ = quesList.get(qid);
        questionName.setText(currentQ.getQuestionName());
        int qno = qid + 1;

        // questionTotal.setText("" + qno + "/" + currentQ.getTotalQuestion());
        List optionList = new ArrayList();

        optionList.add(currentQ.getOptionA());
        optionList.add(currentQ.getOptionB());
        optionList.add(currentQ.getOptionC());
        optionList.add(currentQ.getOptionD());
        Log.e("before", "" + optionList);
        Collections.shuffle(optionList);
        Log.e("after", "" + optionList);
        for (int i = 0; i < optionList.size(); i++) {
           /* opA.setTag(optionList.get(0));
            opB.setTag(optionList.get(1));
            opC.setTag(optionList.get(2));
            opD.setTag(optionList.get(3));*/

            opA.setText(optionList.get(0).toString());
            opB.setText(optionList.get(1).toString());
            opC.setText(optionList.get(2).toString());
            opD.setText(optionList.get(3).toString());
        }


        qid++;

    }

    private void getQuestionData() {
        String tag_string_req = "questionRequest";
        //showProgressDialog();

        questionDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    /*String name = (String) messageSnapshot.child("optionA").getValue().toString();
                    Log.e("name", "" + name);*/

                    QuestionData questionData = new QuestionData(messageSnapshot.child("qid").getValue().toString(), messageSnapshot.child("question").getValue().toString(), messageSnapshot.child("optionA").getValue().toString(),
                            messageSnapshot.child("optionB").getValue().toString(), messageSnapshot.child("optionC").getValue().toString(), messageSnapshot.child("optionD").getValue().toString(), messageSnapshot.child("rightAns").getValue().toString());
                    String rightAns = messageSnapshot.child("rightAns").getValue().toString();
                    if (rightAns.equalsIgnoreCase("A")) {
                        questionData.setTrueAns(messageSnapshot.child("optionA").getValue().toString());
                    } else if (rightAns.equalsIgnoreCase("B")) {
                        questionData.setTrueAns(messageSnapshot.child("optionB").getValue().toString());
                    } else if (rightAns.equalsIgnoreCase("C")) {
                        questionData.setTrueAns(messageSnapshot.child("optionC").getValue().toString());
                    } else {
                        questionData.setTrueAns(messageSnapshot.child("optionD").getValue().toString());
                    }
                    quesList.add(questionData);
                    totalQuestion.setText(""+quesList.size());
                }
                currentQ = quesList.get(qid);
                setQuestion();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    private boolean getNextQuestion(final Button btnA, final Button btnB, final Button btnC, final Button btnD, final Button optionAns, boolean c, String time) {
        setDisable();

        Log.e("ans", "" + optionAns.getText().toString());
        Log.e("currentQ.getAnswer()", "" + currentQ.getTrueAns());
        //setOptionColorAfterTimeIsFinished(opA, opB, opC, opD);
        setOptionColor(btnA, btnB, btnC, btnD, optionAns);
        /*if (time.equalsIgnoreCase("0")) {
            setOptionColorAfterTimeIsFinished(opA, opB, opC, opD);
        } else {
            setOptionColor(btnA, btnB, btnC, btnD, optionAns);
        }*/


        if (!currentQ.getTrueAns().equals(optionAns.getText().toString())) {
            //setWrongOptoionBackground(optionBtn);
            try {
                if (sharedPref.getSound(PlayQuizActivity.this) == true) {
                    setWrongSound();
                }
            } catch (Exception ex) {

            }


            wrongCount++;
            wrongQuestion.setText("" + wrongCount);
            Log.e("wrongCount", "" + wrongCount);

        } else {
            // setRightOptionBackground(optionBtn);
            rightCount++;
            rightQuestion.setText("" + rightCount);
            if (wrongCount == 0) {
                gemsCount = 3;
            }

            totalScore = totalScore + Prefs.SCORE;
            totalScoretxt.setText("" + totalScore);
            try {
                if (sharedPref.getSound(PlayQuizActivity.this) == true) {
                    setRightSound();
                }
            } catch (Exception ex) {

            }

        }
        if (qid < quesList.size()) {
            currentQ = quesList.get(qid);
            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setQuestion();
                        setOptionBackground(btnA, btnB, btnC, btnD);
                        try {
                            // linearTimer.pauseTimer();
                            // linearTimer.restartTimer();
                        } catch (Exception ex) {
                            ex.printStackTrace();

                        }
                    }
                }, 1000);
            } catch (Exception ex) {
                ex.printStackTrace();

            }

            //setTimer();


        } else {
            /*int actualTotalScore = totalScore + firebaseTotalScore;
            int actualTotalStar = gemsCount + firebaseTotalStar;
            int actualTotalGems = gemsCount + firebaseTotalGems;
            if (actualTotalGems <= 3) {
                actualTotalGems = actualTotalGems + Constants.GIVE_GEMS;
            }
            if (TextUtils.isEmpty(userId)) {
                setUserDataInFirebase(sharedPref.getUserName(QuestionActivity.this), sharedPref.getEmail(QuestionActivity.this), sharedPref.getPhotoUrl(QuestionActivity.this), actualTotalStar, actualTotalGems, actualTotalScore, totalScore, sharedPref.getCountryCode(QuestionActivity.this));
            } else {
                updateOrInsertUserDataInFireBase(sharedPref.getUserName(QuestionActivity.this), sharedPref.getEmail(QuestionActivity.this), sharedPref.getPhotoUrl(QuestionActivity.this), actualTotalStar, actualTotalGems, actualTotalScore, totalScore, sharedPref.getCountryCode(QuestionActivity.this));
            }
            try {
                backMusic.stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            linearTimer.pauseTimer();*/
            try {
                backMusic.stop();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            long actualTotalScore = totalScore + firebaseTotalScore;
            Log.e(" quesList.size()",""+ quesList.size());
            Log.e("actualTotalScore",""+actualTotalScore);
            int totalQuestion=quesList.size();
            int starOne=totalQuestion*50/100;
            int starTwo=totalQuestion*70/100;
            int starThree=totalQuestion*80/100;
            int star=0;
            int status=0;
            Log.e("rightCount",""+rightCount);
            if(rightCount>=starOne  && rightCount<starTwo){
                star=1;
                status=0;
                Log.e("1",""+star);
            }else if(rightCount>=starTwo && rightCount<starThree){
                star=2;
                Log.e("2",""+star);
                status=1;
            }else if(rightCount>starTwo && rightCount<=totalQuestion){
                star=3;
                status=1;
                Log.e("3",""+star);
            }else if(rightCount<starOne){
                star=0;
                status=0;
                Log.e("4",""+star);
            }
            if(firebaseLastScore<=totalScore){
                setLeaderbopardDataInFirebase(levelNo,status,actualTotalScore,star);
            }else{
                Log.e(TAG,"NotUpdatet");
            }

            /*if (TextUtils.isEmpty(userId)) {
                setLeaderbopardDataInFirebase(levelNo,status,actualTotalScore,star);
            } else {
                updateOrInsertLeaderboardDataInFireBase(levelNo,status,actualTotalScore,star);
            }*/
            Intent intent = new Intent(PlayQuizActivity.this, GameOverActivity.class);
            intent.putExtra("levelScore", totalScore);
            intent.putExtra("levelNo", levelNo);
            intent.putExtra("star", star);
            PlayQuizActivity.this.startActivity(intent);
            PlayQuizActivity.this.finish();

            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opA_img:
                //time.setText("");

                getNextQuestion(opA, opB, opC, opD, opA, false, "1");
                //ans=opA.getText().toString();
                break;
            case R.id.opB_img:
                //time.setText("");
                // getNextQuestion(opA, opB, opC, opD, opB, false, "1");
                // ans=opB.getText().toString();
                getNextQuestion(opA, opB, opC, opD, opB, false, "1");

                break;
            case R.id.opC_img:
                //time.setText("");
                // getNextQuestion(opA, opB, opC, opD, opC, false, "1");
                // ans=opC.getText().toString();
                getNextQuestion(opA, opB, opC, opD, opC, false, "1");

                break;
            case R.id.opD_img:
                // time.setText("");
                // getNextQuestion(opA, opB, opC, opD, opD, false, "1");
                // ans=opD.getText().toString();
                getNextQuestion(opA, opB, opC, opD, opD, false, "1");
                break;
            case R.id.imgBack:
                gameExitDialog(getString(R.string.stop_game), getString(R.string.exit_game));
                break;
        }

    }

    private void setRightOptionBackground(Button rightOption) {
        rightOption.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle_right));
        rightOption.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.white));

    }

    private void setWrongOptoionBackground(Button wrongOption) {
        wrongOption.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle_wrong));
        wrongOption.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.white));

    }

    private void setOptionBackground(Button btnA, Button btnB, Button btnC, Button btnD) {
        btnA.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.purple));
        btnA.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle));
        btnB.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.purple));
        btnB.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle));
        btnC.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.purple));
        btnC.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle));
        btnD.setTextColor(Utils.getColor(PlayQuizActivity.this, R.color.purple));
        btnD.setBackground(Utils.getDrawable(PlayQuizActivity.this, R.drawable.rounded_circle));
    }


    private void setOptionColor(Button btnA, Button btnB, Button btnC, Button btnD, Button clickBtn) {
        if (currentQ.getTrueAns().equalsIgnoreCase(btnA.getText().toString())) {
            setRightOptionBackground(btnA);
        }
        if (currentQ.getTrueAns().equalsIgnoreCase(btnB.getText().toString())) {
            setRightOptionBackground(btnB);
        }
        if (currentQ.getTrueAns().equalsIgnoreCase(btnC.getText().toString())) {
            setRightOptionBackground(btnC);
        }
        if (currentQ.getTrueAns().equalsIgnoreCase(btnD.getText().toString())) {
            setRightOptionBackground(btnD);
        }

        if (!currentQ.getTrueAns().equalsIgnoreCase(clickBtn.getText().toString())) {
            setWrongOptoionBackground(clickBtn);
        }


    }

    private void setEnable() {
        opA.setEnabled(true);
        opB.setEnabled(true);
        opC.setEnabled(true);
        opD.setEnabled(true);

    }

    private void setDisable() {
        opA.setEnabled(false);
        opB.setEnabled(false);
        opC.setEnabled(false);
        opD.setEnabled(false);
    }

    private void setRightSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.right_ans);
        mediaPlayer.start();
    }

    private void setWrongSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.wronge_ans);
        mediaPlayer.start();

    }

    private void setBackgroundSound() {
        try {
            if (sharedPref.getMusic(PlayQuizActivity.this) == true) {
                backMusic = MediaPlayer.create(this, R.raw.background_music);
                backMusic.start();
            }
        } catch (Exception ex) {

        }
    }

    public void gameExitDialog(String title, String msg) {
        Log.e("Click", "Yes");
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                try {
                    backMusic.stop();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Intent homeIntent = new Intent(PlayQuizActivity.this, LevelActivity.class);
                PlayQuizActivity.this.startActivity(homeIntent);
                PlayQuizActivity.this.finish();

                dialog.dismiss();

            }
        });

        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try {
            if (currentUser != null) {
                Log.e("UID", "" + currentUser.getUid());
            }
        } catch (Exception ex) {

        }

        //updateUI(currentUser);
    }

    // [END on_start_check_user]
    private void signInAnonymously() {
        //showProgressDialog();
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInAnonymously:failure", task.getException());
                            Toast.makeText(PlayQuizActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END signin_anonymously]
    }

    private void signOut() {
        mAuth.signOut();
        // updateUI(null);
    }
    private void setLeaderbopardDataInFirebase(String levelNo,int levelStatus,long totalScore,int star) {
        //  userId = mFirebaseDatabase.push().getKey();
        Leaderboard leaderboard = new Leaderboard(userId,levelNo,levelStatus,totalScore,star);
        mFirebaseDatabase.child(userId).child("levelscore").child(levelNo).setValue(leaderboard);
    }
    private void updateOrInsertLeaderboardDataInFireBase(String levelNo,int levelStatus,long totalScore,int star) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference(Prefs.FB_LEADERBOARD).child(userId).child("levelscore").child(levelNo);

        //updating leaderboard
        Leaderboard leaderboard = new Leaderboard(userId,levelNo,levelStatus,totalScore,star);
        dR.setValue(leaderboard);
    }
    private void getLeaderboardDataFromFirebase() {
        Log.e("getCall", "getCall");

        //attaching value event listener
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterating through all the nodes
                try {

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                    {
                        //getting artist
                        long noOfChile = postSnapshot.getChildrenCount();

                        if (userId.equalsIgnoreCase(postSnapshot.getKey()))
                        {
                            for (DataSnapshot dsn : postSnapshot.getChildren()){
                                Log.e(TAG, "dsn " + dsn.getValue());

                                int i = 0;
                                for (DataSnapshot children : dsn.getChildren()){
                                    Log.e(TAG, "child : " + children.getKey() + " : " + children.getValue());
                                    JSONObject jsonObject = new JSONObject("" + children.getValue());
                                    if(levelNo.equalsIgnoreCase(jsonObject.getString("levelNo"))){
                                        firebaseLastScore=Long.parseLong(jsonObject.getString("score"));
                                        Log.e(TAG, "Lastscore : " +firebaseLastScore);
                                    }

                                    i++;
                                }

                            }
                            Log.e(TAG, "Same user will be founded");
                        }
                    }

                } catch (Exception ex) {
                    Log.e("nul", "" + ex.toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        gameExitDialog(getString(R.string.stop_game), getString(R.string.exit_game));
    }

    private void init() {
        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(userId==null){
            //signInAnonymously();
        }
        rightQuestion = (TextView) findViewById(R.id.txtTrueQue);
        rightQuestion.setText("0");
        wrongQuestion = (TextView) findViewById(R.id.txtWrongQue);
        wrongQuestion.setText("0");
        totalQuestion = (TextView) findViewById(R.id.txtNoOfQue);
        totalScoretxt = (TextView) findViewById(R.id.txtScore);
        totalScoretxt.setText("00");
        levelNameTxt = (TextView) findViewById(R.id.txtLevelName);
        backImg = (ImageView) findViewById(R.id.imgBack);

        questionName = (TextView) findViewById(R.id.txtQuestion);
        opA = (Button) findViewById(R.id.opA_img);
        opB = (Button) findViewById(R.id.opB_img);
        opC = (Button) findViewById(R.id.opC_img);
        opD = (Button) findViewById(R.id.opD_img);

        /*opA=(TextView) findViewById(R.id.opA);
        opB=(TextView) findViewById(R.id.opB);
        opC=(TextView) findViewById(R.id.opC);
        opD=(TextView) findViewById(R.id.opD);*/
        quesList = new ArrayList<>();
    }


}
