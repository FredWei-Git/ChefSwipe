package com.uottawahack.chefswipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.ViewDragHelper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class SwipeActivity extends AppCompatActivity {
    private SwipeViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Update UI when data is changed
        vm.makeSwipeRequest("chicken", "alcohol-free");

    }
    private class ViewDragHelperCallback extends ViewDragHelper.Callback{
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return false;
        }
    }
}