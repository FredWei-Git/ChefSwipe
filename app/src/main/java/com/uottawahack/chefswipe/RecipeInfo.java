package com.uottawahack.chefswipe;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeInfo<T> {
    // Variables
    private String name, recipeURL, image;
    private ArrayList<String> ingredients = new ArrayList<String>();
    private ArrayList<String> healthLabels = new ArrayList<String>();

    // Temp variables
    JSONArray tempArray;

    // Constructors
    private RecipeInfo() {
    }

    public RecipeInfo(JSONArray arr, JSONObject response) {
        try {
            // generating random number from first 10 results
            /*int lower = 0;
            int upper = 1;
            int r = (int) (Math.random() * (upper - lower)) + lower; //  lower (inclusive) and upper (exclusive)
             */
            int r = 0;
            // setting the name, recipeURL, ingredients, and healthLabels
            this.name = arr.getJSONObject(r).getJSONObject("recipe").getString("label");
            this.recipeURL = arr.getJSONObject(r).getJSONObject("recipe").getString("url");
            this.image = arr.getJSONObject(r).getJSONObject("recipe").getString("image");
            tempArray = arr.getJSONObject(r).getJSONObject("recipe").getJSONArray("ingredientLines");
            for (int i = 0; i < tempArray.length(); i++) {
                this.ingredients.add((String) tempArray.get(i));
            }
            tempArray = arr.getJSONObject(r).getJSONObject("recipe").getJSONArray("healthLabels");
            for (int i = 0; i < tempArray.length(); i++) {
                this.healthLabels.add((String) tempArray.get(i));
            }
            // Output random recipe generated
            Log.e(String.valueOf(r), name);
            Log.e(String.valueOf(r), recipeURL);
            Log.e(String.valueOf(r), image);
            Log.e(String.valueOf(r), ingredients.get(0));
            Log.e(String.valueOf(r), healthLabels.get(0));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Testing JSON parse", "error");
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getRecipeURL() {
        return recipeURL;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getHealthLabels() {
        return healthLabels;
    }


    // Success or Error
    public static final class Success<T> extends RecipeInfo<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends RecipeInfo<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}
