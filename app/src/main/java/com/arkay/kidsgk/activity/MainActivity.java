package com.arkay.kidsgk.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.application.MainApplication;
import com.arkay.kidsgk.beans.Leaderboard;
import com.arkay.kidsgk.fragment.FragmentGameOver;
import com.arkay.kidsgk.fragment.ImagePlayQuizFragment;
import com.arkay.kidsgk.fragment.KidsPlayQuizFragment;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ImagePlayQuizFragment.Listener,FragmentGameOver.Listener,KidsPlayQuizFragment.Listener{

    Button imgPlayQuiz,imgImageQuiz,imgKidsGk;
    ImageButton btnSetting;
    Dialog dialog;
    FragmentTransaction ft;
    private ImagePlayQuizFragment imagePlayQuizFragment;
    private FragmentGameOver fragmentGameOver;
    private KidsPlayQuizFragment kidsPlayQuizFragment;
    private Typeface tpMarkOne;
    SessionManagerSharedPref sharedPref=SessionManagerSharedPref.getInstance();
    private FirebaseAuth mAuth;
    private String userId;
    private  Prefs prefs;
    private DatabaseReference mFirebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance().getReference(Prefs.FB_LEADERBOARD);
        //getLeaderboardDataFromFirebase();
        prefs = MainApplication.getInstance().getPrefs();
        Log.e("UUUU",""+prefs.getUID());
        Log.e("UUUU_ONE",""+Prefs.USER_ID);
        if(prefs.getUID().equalsIgnoreCase(Prefs.USER_ID)){
            signInAnonymously();
        }else{
            Log.e("USERAledeySignIn","USERAledeySignIn");
        }
       /* try{
           if(prefs.getUID()==){
               signInAnonymously();
           }else{
               Log.e("USERAledeySignIn","USERAledeySignIn");
           }

        }catch(Exception ex){
            Log.e("nbnbnnbn",""+ex.toString());

        }*/
        imgPlayQuiz = (Button) findViewById(R.id.imgPlayQuiz);
        imgImageQuiz = (Button) findViewById(R.id.imgImageQuiz);
        imgKidsGk = (Button) findViewById(R.id.imgKidsGk);
        imgPlayQuiz.setOnClickListener(this);
        imgImageQuiz.setOnClickListener(this);
        imgKidsGk.setOnClickListener(this);

        dialog = new Dialog(this);
        tpMarkOne = MainApplication.getInstance().getFontMarkoOneRegular();

        btnSetting = (ImageButton) findViewById(R.id.btnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialoug_setting);
                TextView settingtxt=(TextView)dialog.findViewById(R.id.setting_text);

                final ToggleButton musicToggle=(ToggleButton) dialog.findViewById(R.id.music_toggle);
                final ToggleButton soundToggle=(ToggleButton) dialog.findViewById(R.id.sound_toggle);
                try{
                    if(sharedPref.getMusic(MainActivity.this)==true){
                        musicToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_on));
                    }else{
                        musicToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_off));
                    }
                    if(sharedPref.getSound(MainActivity.this)==true){
                        soundToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_on));
                    }else{
                        soundToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_off));
                    }
                }catch(Exception ex){
                }

                musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            sharedPref.setMusic(MainActivity.this,true);
                            musicToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_on));
                        }
                        else{
                            sharedPref.setMusic(MainActivity.this,false);
                            musicToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_off));
                        }
                    }
                });
                soundToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked)
                        {
                            sharedPref.setSound(MainActivity.this,true);
                            soundToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_on));
                        }
                        else{
                            sharedPref.setSound(MainActivity.this,false);
                            soundToggle.setBackground(Utils.getDrawable(MainActivity.this, R.drawable.switch_off));
                        }
                    }
                });
                ImageView homeImg=(ImageView) dialog.findViewById(R.id.home_img);
                TextView musictxt=(TextView)dialog.findViewById(R.id.music_text);
                TextView soundtxt=(TextView)dialog.findViewById(R.id.sound_text);
                settingtxt.setTypeface(tpMarkOne);
                musictxt.setTypeface(tpMarkOne);
                soundtxt.setTypeface(tpMarkOne);
                homeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

    private void signInAnonymously() {
        //showProgressDialog();
        // [START signin_anonymously]
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ss", "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            prefs.setUID(mAuth.getCurrentUser().getUid());
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("sdsd", "signInAnonymously:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.imgPlayQuiz:
                Log.i("imgPlayQuiz","");
                Intent intent = new Intent(getApplicationContext(), LevelActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                MainActivity.this.finish();
                break;
            case R.id.imgKidsGk:
                //gamOver();
                getKidsQuiz();
                break;
            case R.id.imgImageQuiz:
                getImageQuiz();
                break;
        }
    }
    public void getImageQuiz(){

        Bundle bundle = new Bundle();
        imagePlayQuizFragment = ImagePlayQuizFragment.newInstance(bundle);
        imagePlayQuizFragment.setListener(this);
        ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        ft.replace(R.id.activity_main, imagePlayQuizFragment).addToBackStack(null).commit();
    }

    public void gamOver(){
        Bundle bundle = new Bundle();
        fragmentGameOver = FragmentGameOver.newInstance(bundle);
        fragmentGameOver.setListener(this);
        ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        ft.replace(R.id.activity_main, fragmentGameOver).addToBackStack(null).commit();
    }
    public void getKidsQuiz(){

        Bundle bundle = new Bundle();
        kidsPlayQuizFragment = KidsPlayQuizFragment.newInstance(bundle);
        kidsPlayQuizFragment.setListener(this);
        ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left,R.anim.anim_slide_in_right, R.anim.anim_slide_out_right);
        ft.replace(R.id.activity_main, kidsPlayQuizFragment).addToBackStack(null).commit();
    }

    private void getLeaderboardDataFromFirebase() {
        Log.e("getCall", "getCall");
        //attaching value event listener
        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long totalscore=0;

                //iterating through all the nodes
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Leaderboard leaderboard = postSnapshot.child(userId).child("levelscore").child(""+i).getValue(Leaderboard.class);

                    if (userId.equalsIgnoreCase(leaderboard.getId())) {
                        totalscore =totalscore+ leaderboard.getScore();
                        Log.e("totaSSSS" + "", "" + totalscore);
                    }
                    i++;

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
