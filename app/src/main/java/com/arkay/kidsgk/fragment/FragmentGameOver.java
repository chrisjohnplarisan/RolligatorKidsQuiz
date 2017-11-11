package com.arkay.kidsgk.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkay.kidsgk.R;
import com.arkay.kidsgk.application.MainApplication;

/**
 * Created by India on 27-07-2017.
 */

public class FragmentGameOver extends Fragment {


    private View view;
    private TextView txtSuccessMsg,txtLevel,lblTotalScore,txtTotalScore,lblLevelScore,txtLevelScore;


    private Typeface tpMarkOne;
    public interface Listener {

    }

    Listener mListener = null;
    public static FragmentGameOver newInstance(Bundle bundle) {
        FragmentGameOver fragment = new FragmentGameOver();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_over, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        tpMarkOne = MainApplication.getInstance().getFontMarkoOneRegular();
        txtLevelScore = (TextView) view.findViewById(R.id.txtLevelScore);
        txtSuccessMsg = (TextView) view.findViewById(R.id.txtSuccessMsg);
        txtLevel = (TextView) view.findViewById(R.id.txtLevel);
        lblTotalScore = (TextView) view.findViewById(R.id.lblTotalScore);
        txtTotalScore = (TextView) view.findViewById(R.id.txtTotalScore);
        lblLevelScore = (TextView) view.findViewById(R.id.lblLevelScore);

        txtLevel.setTypeface(tpMarkOne);
        txtLevelScore.setTypeface(tpMarkOne);
        txtSuccessMsg.setTypeface(tpMarkOne);
        lblTotalScore.setTypeface(tpMarkOne);
        txtTotalScore.setTypeface(tpMarkOne);
        lblLevelScore.setTypeface(tpMarkOne);

    }
}
