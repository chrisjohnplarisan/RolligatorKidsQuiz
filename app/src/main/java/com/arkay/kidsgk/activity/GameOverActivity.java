package com.arkay.kidsgk.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.adapter.GkLevelAdpter;
import com.arkay.kidsgk.application.MainApplication;
import com.arkay.kidsgk.beans.Leaderboard;
import com.arkay.kidsgk.beans.MathsLevel;
import com.arkay.kidsgk.utils.Prefs;
import com.arkay.kidsgk.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.List;

public class GameOverActivity extends AppCompatActivity {
    private ImageView starOne,starTwo,starThree;
    private View view;
    private TextView txtSuccessMsg,txtLevel,txtTotalScore,txtLevelScore;
    private ImageButton homeImgBtn;
    private DatabaseReference mFirebaseDatabase;
    private String userId;
    private long firebaseTotalScore;
    private int star;
private  String levelNo;
    private  String TAG=GameOverActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_over);
        init();
        levelNo=getIntent().getStringExtra("levelNo");
        star=getIntent().getIntExtra("star",0);
        displayStar(star);
        if(star>=2){
            txtLevel.setText(getResources().getString(R.string.quiz_level_msg)+" "+levelNo+" "+getResources().getString(R.string.quiz_level_complate_msg));
            txtSuccessMsg.setText(getResources().getString(R.string.great_job));
        }else{
            txtLevel.setText(getResources().getString(R.string.quiz_level_msg)+" "+levelNo+" "+getResources().getString(R.string.quiz_level_fail_msg));
            txtSuccessMsg.setText(getResources().getString(R.string.quiz_level_fail_msg));
        }
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(Prefs.FB_LEADERBOARD);
        getLeaderboardDataFromFirebase();
        homeImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                GameOverActivity.this.startActivity(intent);
                GameOverActivity.this.finish();
            }
        });
    }
    private void setDrawableStar(ImageView img){
        img.setBackground(Utils.getDrawable(GameOverActivity.this,R.drawable.star1));
    }
    private void setDrawableNoStar(ImageView img){
        img.setBackground(Utils.getDrawable(GameOverActivity.this,R.drawable.no_star));
    }
    private void displayStar(int star){
        switch (star){
            case 0:
                setDrawableNoStar(starOne);
                setDrawableNoStar(starTwo);
                setDrawableNoStar(starThree);
                break;
            case 1:
                setDrawableStar(starOne);
                setDrawableNoStar(starTwo);
                setDrawableNoStar(starThree);
                break;
            case 2:
                setDrawableStar(starOne);
                setDrawableStar(starTwo);
                setDrawableNoStar(starThree);
                break;
            case 3:
                setDrawableStar(starOne);
                setDrawableStar(starTwo);
                setDrawableStar(starThree);
                break;
        }
    }
    private void getLeaderboardDataFromFirebase() {
        Log.e("getCall", "getCall");

        //attaching value event listener
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalscore=0;
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
                                    String score = jsonObject.getString("score");
                                    Log.e(TAG, "score : " + score);
                                    totalscore=totalscore+Long.parseLong(score);
                                    Log.e(TAG, "totalscore : " + totalscore);
                                    txtTotalScore.setText(""+totalscore);
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
        Intent intent = new Intent(GameOverActivity.this, LevelActivity.class);
        GameOverActivity.this.startActivity(intent);
        GameOverActivity.this.finish();
    }

    private void init(){
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        txtLevelScore = (TextView)findViewById(R.id.txtLevelScore);
        txtSuccessMsg = (TextView)findViewById(R.id.txtSuccessMsg);
        homeImgBtn = (ImageButton) findViewById(R.id.home_img_btn);
        txtLevel = (TextView)findViewById(R.id.txtLevel);
        txtTotalScore = (TextView)findViewById(R.id.txtTotalScore);
        txtLevelScore.setText(""+getIntent().getIntExtra("levelScore",0));

        starOne = (ImageView) findViewById(R.id.star_one);
        starTwo = (ImageView) findViewById(R.id.star_two);
        starThree = (ImageView) findViewById(R.id.star_three);

    }
}
