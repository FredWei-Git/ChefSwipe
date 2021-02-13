package com.uottawahack.chefswipe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.material.card.MaterialCardView;

public class MaterialCardViewTouchListener extends MaterialCardView {
    public MaterialCardViewTouchListener(Context context) {
        super(context);
    }

    public MaterialCardViewTouchListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialCardViewTouchListener(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //Intercepts touches to CardView when user moves their touch
        final int action = e.getAction();
        return action == MotionEvent.ACTION_MOVE;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //Applies the default touch event
        super.onTouchEvent(e);
        performClick();
        return true;
    }
    @Override
    public boolean performClick() {
        // default perform click
        super.performClick();
        return true;
    }
}
