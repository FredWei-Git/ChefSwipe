package com.uottawahack.chefswipe;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


public class SwipeViewModel extends AndroidViewModel {
    private static Context context;

    private final LiveData<RecipeInfo> recipeLiveData = null;
    private SavedStateHandle state;

    public SwipeViewModel(Application application){
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<RecipeInfo> getRecipe() {
        return recipeLiveData;
    }

    void makeSwipeRequest(String ingredients, String health) {
        String app_id = "3c7db970";
        String app_key = "b9151b2fbebd7585310c64eaf7373789";
        String prefixURL = "https://api.edamam.com/search";

        String fullURL = prefixURL + "?q=" + ingredients + "&app_id=" + app_id +
                "&app_key=" + app_key + "&health=" + health;

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                fullURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        SwipeRepository.getInstance(context).addToRequestQueue(objectRequest);
    }
}