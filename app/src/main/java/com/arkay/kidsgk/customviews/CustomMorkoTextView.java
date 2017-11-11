package com.arkay.kidsgk.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

import com.arkay.kidsgk.R;

/**
 * Created by 2 on 11/5/2017.
 */

public class CustomMorkoTextView extends TextView {
    public CustomMorkoTextView(Context context) {
        super(context);
        init(null);

    }

    public CustomMorkoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public CustomMorkoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomMorkoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

   /* private void init(AttributeSet attrs) {
        if(attrs!=null)
        {
            if (attrs != null) {

                TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);

                String fontName = a.getString(R.styleable.CustomTextView_font);


                try {
                    if (fontName != null) {
                        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + "Audiowide-Regular.ttf");
                        setTypeface(myTypeface);
                    }
                 } catch (Exception e) {
                    e.printStackTrace();
                 }
                a.recycle();
                }
        }

    }*/

    protected void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTextView);
            String fontName = a.getString(R.styleable.CustomTextView_fontName);
            Typeface myTypeface = null;
            if (fontName != null) {
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "" + fontName);
                setTypeface(myTypeface);
            } else {
                myTypeface = Typeface.createFromAsset(getContext().getAssets(), "MarkoOne-Regular.ttf");
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }


}
