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
 * Created by India on 05-07-2017.
 */

public class KidsPlayQuizFragment extends Fragment {


    private View view;
    private TextView txtLevelName,txtScore,txtNoOfQue,txtTrueQue,txtWrongQue,txtQuestion;


    private Typeface tpMarkOne;
    public interface Listener {

    }

    Listener mListener = null;
    public static KidsPlayQuizFragment newInstance(Bundle bundle) {
        KidsPlayQuizFragment fragment = new KidsPlayQuizFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setListener(Listener l) {
        mListener = l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kids_play_quiz, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        tpMarkOne = MainApplication.getInstance().getFontMarkoOneRegular();
        txtLevelName = (TextView)view.findViewById(R.id.txtLevelName);
        txtScore = (TextView)view.findViewById(R.id.txtScore);
        txtNoOfQue = (TextView)view.findViewById(R.id.txtNoOfQue);
        txtTrueQue = (TextView)view.findViewById(R.id.txtTrueQue);
        txtWrongQue = (TextView)view.findViewById(R.id.txtWrongQue);
        txtQuestion = (TextView)view.findViewById(R.id.txtQuestion);
        txtLevelName.setTypeface(tpMarkOne);
        txtScore.setTypeface(tpMarkOne);
        txtNoOfQue.setTypeface(tpMarkOne);
        txtTrueQue.setTypeface(tpMarkOne);
        txtWrongQue.setTypeface(tpMarkOne);
        txtQuestion.setTypeface(tpMarkOne);
    }
}
