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
    // random food variable
    private String randomFood;
    // firebase
    FirebaseAuth mAuth = FirebaseAuth.getInstance(); //Grabs current instance of database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

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
        //Obtain Random ingredient *temp*
        randomFood = vm.makeIngredientsRequest();
        //Obtain recipe for ingredient
        vm.makeSwipeRequest(randomFood, "alcohol-free");
        //Update UI when data is changed
        vm.getRecipe().observe(this, RecipeInfo -> {
            updateUI();
        });
    }
    //Updates ui
    private void updateUI() {
        RecipeInfo recipe = vm.getRecipe().getValue();
        TextView recipeName = (TextView) findViewById(R.id.recipeNameView);
        ImageView imageView = (ImageView) findViewById(R.id.recipeImageView);
        new DownloadImageTask(imageView).execute(recipe.getImage());

        recipeName.setText(recipe.getName());
    }
    //Download Recipe Picture from link obtained
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
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
            // adding food to user's food database
            if (mFirebaseUser != null) {
                String user;
                user = mFirebaseUser.getUid(); //Do what you need to do with the id
                if (randomFood != null && vm.getRecipe().getValue().getRecipeURL() != null) {
                    Map<String, Object> savedRecipes = new HashMap<>();
                    savedRecipes.put("Recipe Name", randomFood);
                    savedRecipes.put("Recipe Link", vm.getRecipe().getValue().getRecipeURL());
                    db.collection("users").document(user).collection("SavedRecipes").document(randomFood).set(savedRecipes);
                }
            }
            // Changes the food image
            ((MotionLayout) view.getParent()).transitionToEnd();
            randomFood = vm.makeIngredientsRequest();
            vm.makeSwipeRequest(randomFood, "alcohol-free");
        } else if (i == R.id.nextButton) {
            randomFood = vm.makeIngredientsRequest();
            vm.makeSwipeRequest(randomFood, "alcohol-free");
        } else if (i == R.id.infoButton) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
    }
}