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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SwipeViewModel extends AndroidViewModel {
    //Obtain context to query
    private static Context context;

    //Mutable data
    private final MutableLiveData<RecipeInfo> recipeLiveData = new MutableLiveData<>();

    //Placeholder ingredient
    private String randomFood = "chicken";
    private SavedStateHandle state;

    // firebase
    FirebaseAuth mAuth = FirebaseAuth.getInstance(); //Grabs current instance of database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

    public SwipeViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<RecipeInfo> getRecipe() {
        return recipeLiveData;
    }


    private void makeSwipeRequest(boolean liked, String ingredients, String health) {
        // API Search Information
        String app_id = "3c7db970";
        String app_key = "@secrets/recipeKey";
        String prefixURL = "https://api.edamam.com/search";
        //Piecing together URL query
        String fullURL = prefixURL + "?q=" + ingredients + "&app_id=" + app_id +
                "&app_key=" + app_key + "&health=" + health;
        //Creating JsonObject request
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                fullURL,
                null,
                response -> {
                    Log.e("Rest Response", response.toString());
                    // Create RecipeInfo Object
                    JSONArray arr;
                    try {
                        // gets the array of recipes in hits json array "[]"
                        arr = response.getJSONArray("hits");
                        //Updating livedata
                        recipeLiveData.setValue(new RecipeInfo(arr, response));
                        //Adds recipe to user's database only if theyliked it
                        if (liked){
                            if (mFirebaseUser != null) {
                                String user;
                                user = mFirebaseUser.getUid();
                                if (randomFood != null &&
                                        Objects.requireNonNull(
                                                recipeLiveData.getValue())
                                                .getRecipeURL() != null) {
                                    Map<String, Object> savedRecipes = new HashMap<>();
                                    savedRecipes.put("Recipe Name", randomFood);
                                    savedRecipes.put(
                                            "Recipe Link",
                                            recipeLiveData.getValue()
                                            .getRecipeURL());
                                    db.collection("users")
                                            .document(user)
                                            .collection("SavedRecipes")
                                            .document(randomFood)
                                            .set(savedRecipes);
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("Rest Response", error.toString())
        );
        //Send request to queue
        SwipeRepository.getInstance(context).addToRequestQueue(objectRequest);
    }

    void makeIngredientsRequest(boolean liked){
        // generating random food from random meal api
        String URL = "https://www.themealdb.com/api/json/v1/1/random.php";
        //Creating JsonObject request
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                response -> {
                    // generating random meal name from API
                    JSONArray arr;
                    try {
                        arr = response.getJSONArray("meals");
                        randomFood = arr.getJSONObject(0).getString("strMeal");
                        makeSwipeRequest(liked, randomFood, "alcohol-free");

                        // Call swipe request for random food.
                        //Log.e("Random Meal", randomFood);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("Random Meal", randomFood);
                },
                error -> Log.e("Rest Response", error.toString())
        );
        SwipeRepository.getInstance(context).addToRequestQueue(objectRequest);
    }
}