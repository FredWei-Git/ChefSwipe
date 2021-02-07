package com.uottawahack.chefswipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.MotionEventCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {
    //Viewmodel instance
    private SwipeViewModel vm;
    private MaterialCardView card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        //Adding button listener
        findViewById(R.id.settingsButton).setOnClickListener(this);
        findViewById(R.id.likeButton).setOnClickListener(this);
        findViewById(R.id.nextButton).setOnClickListener(this);
        findViewById(R.id.infoButton).setOnClickListener(this);
        card = findViewById(R.id.recipeCardView);
        //Instantiate viewmodel
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Obtain Random ingredient *temp* and calls for recipe using ingredient
        vm.makeIngredientsRequest();
        //Update UI when SwipeViewModel.recipeLiveData is changed
        vm.getRecipe().observe(this, RecipeInfo -> {
            updateUI();
        });
    }
    //Updates ui
    private void updateUI() {
        RecipeInfo recipe = vm.getRecipe().getValue();
        TextView recipeName = (TextView) findViewById(R.id.recipeNameView);
        ImageView imageView = (ImageView) findViewById(R.id.recipeImageView);
        if (recipe != null) {
            Picasso.get().load(recipe.getImage()).into(imageView);
        }
        if (recipe != null) {
            if (!recipe.getName().equals("")){
                recipeName.setText(recipe.getName());
            }else {
                recipeName.setText("recipe limit exhausted refresh in a minute");
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
            vm.makeIngredientsRequest();
        } else if (i == R.id.nextButton) {
            vm.makeIngredientsRequest();
        } else if (i == R.id.infoButton) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
    }
}