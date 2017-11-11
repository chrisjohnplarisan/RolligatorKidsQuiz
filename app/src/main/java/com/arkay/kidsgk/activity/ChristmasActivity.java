package com.arkay.kidsgk.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.arkay.kidsgk.R;

public class ChristmasActivity extends AppCompatActivity {
    private TextView chirismusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christmas);
        init();

    }
    private void init(){
        chirismusText=(TextView)findViewById(R.id.gk_notes);
        chirismusText.setMovementMethod(new ScrollingMovementMethod());

    }
}
