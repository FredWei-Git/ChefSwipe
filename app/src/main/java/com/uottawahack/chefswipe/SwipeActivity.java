package com.uottawahack.chefswipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;


public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {
    //Viewmodel instance
    private SwipeViewModel vm;
    private MaterialCardViewTouchListener card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        //Adding button listener
        findViewById(R.id.settingsButton).setOnClickListener(this);
        findViewById(R.id.likeButton).setOnClickListener(this);
        findViewById(R.id.nextButton).setOnClickListener(this);
        findViewById(R.id.infoButton).setOnClickListener(this);

        //Finds SwipeCardView
        card = findViewById(R.id.recipeCardView);
        //Adds TouchListener to Card to respond to user swipes
        card.setOnTouchListener(new View.OnTouchListener() {
            private float previousX;
            private float previousY;
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                view.onTouchEvent(e);
                view.performClick();
                if (e.getAction() == MotionEvent.ACTION_DOWN){
                    previousX = e.getX();
                    previousY = e.getY();
                }
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    float x = e.getX();
                    float y = e.getY();
                    float dx = x - previousX;
                    float dy = y - previousY;
                    if (dy < -650){
                        Log.e("Info", "dy:" + dy);
                    } else if (dx > 100) {
                        //Animates card and refreshes recipe on swipe
                        ((MotionLayout) card.getParent()).transitionToEnd();
                        vm.makeIngredientsRequest(true);
                        Log.e("Liked", "dx:" + dx);
                    } else if (dx < -100){
                        vm.makeIngredientsRequest(false);
                        Log.e("Passed", "dx:" + dx);
                    }

                }

                return true;
            }
        });
        //Instantiate viewmodel
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Obtain Random ingredient *temp* and calls for recipe using ingredient
        vm.makeIngredientsRequest(false);
        //Update UI when SwipeViewModel.recipeLiveData is changed
        vm.getRecipe().observe(this, RecipeInfo -> updateUI());
    }
    //Updates ui
    private void updateUI() {
        RecipeInfo recipe = vm.getRecipe().getValue();
        TextView recipeName = findViewById(R.id.recipeNameView);
        ImageView imageView = findViewById(R.id.recipeImageView);
        if (recipe != null) {
            Picasso.get().load(recipe.getImage()).into(imageView);
        }
        if (recipe != null) {
            if (!recipe.getName().equals("")){
                recipeName.setText(recipe.getName());
            }else {
                recipeName.setText(getString(R.string.limitText));
            }
        }
    }

    //Button click listener
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.settingsButton) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        } else if (i == R.id.likeButton) {
            // Changes the food image
            ((MotionLayout) view.getParent()).transitionToEnd();
            vm.makeIngredientsRequest(true);
        } else if (i == R.id.nextButton) {
            vm.makeIngredientsRequest(false);
        } else if (i == R.id.infoButton) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
    }
}