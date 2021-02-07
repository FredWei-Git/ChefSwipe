package com.uottawahack.chefswipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

import com.google.android.material.card.MaterialCardView;


public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {
    private SwipeViewModel vm;
    private MaterialCardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        findViewById(R.id.settingsButton).setOnClickListener(this);

        card = findViewById(R.id.recipeCardView);
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        vm.makeSwipeRequest("chicken", "alcohol-free");
        //Update UI when data is changed
        vm.getRecipe().observe(this, RecipeInfo -> {
            updateUI();
        });
    }
    private void updateUI(){

    }
    private class ViewDragHelperCallback extends ViewDragHelper.Callback {
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

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.settingsButton) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }else if (i == R.id.likeButton){

        }else if (i == R.id.nextButton){

        }else if (i == R.id.infoButton){

        }
    }
}