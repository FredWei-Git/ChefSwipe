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
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SwipeActivity extends AppCompatActivity implements View.OnClickListener {
    private SwipeViewModel vm;
    private MaterialCardView card;
    // random food variable
    private String randomFood = "chicken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        findViewById(R.id.settingsButton).setOnClickListener(this);

        card = findViewById(R.id.recipeCardView);
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);

        // generating random food form random meal api
        String URL = "https://www.themealdb.com/api/json/v1/1/random.php";
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // generating random meal name from API
                        JSONArray arr = null;
                        try {
                            arr = response.getJSONArray("meals");
                            randomFood = arr.getJSONObject(0).getString("strMeal");
                            // Call swipe request for random food.
                            //Log.e("Random Meal", randomFood);
                            vm.makeSwipeRequest(randomFood, "alcohol-free");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.e("Random Meal", randomFood);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());

                    }
                }
        );
        requestQueue.add(objectRequest);
        // vm.makeSwipeRequest(randomFood, "alcohol-free");
        //Update UI when data is changed
        vm.getRecipe().observe(this, RecipeInfo -> {
        });
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
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }
}