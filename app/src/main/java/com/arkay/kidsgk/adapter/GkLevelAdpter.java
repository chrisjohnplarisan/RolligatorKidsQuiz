package com.arkay.kidsgk.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.application.MainApplication;
import com.arkay.kidsgk.beans.MathsLevel;
import com.arkay.kidsgk.utils.Utils;

import java.util.List;



public class GkLevelAdpter extends BaseAdapter {
    private Activity activity;
    private List<MathsLevel> mathsLevelList;
    private String[] bgColors;
    private Typeface tp;
    private TextView txtNumber, txtScore;
    private RatingBar ratingBar;
    private TextView textView3;
    private ImageView imgLock,starOne,starTwo,starThree;
    private RelativeLayout rel1;
    private Typeface tpMarkOne;



    public GkLevelAdpter(Activity activity, List<MathsLevel> mathsLevelList) {
        this.activity = activity;
        this.mathsLevelList = mathsLevelList;
       // bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.serial_level);

    }

    @Override
    public int getCount() {
        return mathsLevelList.size();
    }

    @Override
    public MathsLevel getItem(int location) {
        return mathsLevelList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v;
        if (convertView == null) {
            LayoutInflater li = activity.getLayoutInflater();
            v = li.inflate(R.layout.single_level, null);
        } else {
            v = convertView;
        }

        MathsLevel moLevel = mathsLevelList.get(position);
        Log.e("rat",""+moLevel.getLevelRating());
        Log.e("status",""+moLevel.getLevelStaus());
        txtNumber = (TextView) v.findViewById(R.id.txtNumber);
        txtScore = (TextView) v.findViewById(R.id.txtScore);
        starOne = (ImageView) v.findViewById(R.id.star_one);
        starTwo = (ImageView) v.findViewById(R.id.star_two);
        starThree = (ImageView) v.findViewById(R.id.star_three);

        switch (moLevel.getLevelRating()){
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
        txtNumber.setText(""+mathsLevelList.get(position).getLevelName());
        txtNumber.setTag(mathsLevelList.get(position).getLevelNo());
        //txtScore.setText(""+mathsLevelList.get(position).getLevelScore());

        tpMarkOne = MainApplication.getInstance().getFontMarkoOneRegular();
        txtNumber.setTypeface(tpMarkOne);
        txtScore.setTypeface(tpMarkOne);
        txtScore.setText(""+moLevel.getLevelScore());
        txtScore.setTag(""+moLevel.getLevelStaus());
        return v;
    }
    private void setDrawableStar(ImageView img){
        img.setBackground(Utils.getDrawable(activity,R.drawable.star1));
    }
    private void setDrawableNoStar(ImageView img){
        img.setBackground(Utils.getDrawable(activity,R.drawable.no_star));
    }

}
