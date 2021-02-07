package com.uottawahack.chefswipe;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SwipeViewModel extends AndroidViewModel {
    //Obtain context to query
    private static Context context;

    //Mutable data
    private final MutableLiveData<RecipeInfo> recipeLiveData = new MutableLiveData<>();


    private String randomFood = "chicken";
    private SavedStateHandle state;

    public SwipeViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<RecipeInfo> getRecipe() {
        return recipeLiveData;
    }


    void makeSwipeRequest(String ingredients, String health) {
        // API Search Information
        String app_id = "3c7db970";
        String app_key = "b9151b2fbebd7585310c64eaf7373789";
        String prefixURL = "https://api.edamam.com/search";
        //Piecing together URL query
        String fullURL = prefixURL + "?q=" + ingredients + "&app_id=" + app_id +
                "&app_key=" + app_key + "&health=" + health;
        //Creating JsonObject request
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                fullURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        // Create RecipeInfo Object
                        JSONArray arr;
                        try {
                            // gets the array of recipes in hit json array "[]"
                            arr = response.getJSONArray("hits");
                            //Updating livedata
                            recipeLiveData.setValue(new RecipeInfo(arr, response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );
        //Send request to queue
        SwipeRepository.getInstance(context).addToRequestQueue(objectRequest);
    }

    String makeIngredientsRequest(){
        // generating random food from random meal api
        String URL = "https://www.themealdb.com/api/json/v1/1/random.php";
        //Creating JsonObject request
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
        SwipeRepository.getInstance(context).addToRequestQueue(objectRequest);
        return randomFood;
    }
}