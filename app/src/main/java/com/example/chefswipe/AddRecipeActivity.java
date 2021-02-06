package com.example.chefswipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity {

    //Firebase
    private FirebaseAuth mAuth;
    // Variables
    Button back, submitRecipe;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        // Firebase
        mAuth = FirebaseAuth.getInstance();

        // Variables
        name = (TextView) findViewById(R.id.addRecipeName);

        // Buttons
        back = (Button) findViewById(R.id.addRecipeBack);
        submitRecipe = (Button) findViewById(R.id.addRecipeSubmit);
        back.setOnClickListener(new View.OnClickListener() {
            // goes back to home page
            public void onClick(View view) {
                //
                finish();
            }
        });
        submitRecipe.setOnClickListener(new View.OnClickListener() {
            // Adds recipe to firebase
            public void onClick(View view) {
                //
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser(); //Do what you need to do with the id
                String user = mFirebaseUser.getUid(); //Do what you need to do with the id
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> recipe = new HashMap<>();
                recipe.put("Recipe Name", name.getText().toString().trim());
                // save recipe to user
                DocumentReference userDB = db.collection("users").document(user).collection("Accounts").document("AccountDetails");
                userDB.collection("Recipes").document(name.getText().toString().trim()).set(recipe);
                // save recipe to main database
                DocumentReference recipeDB = db.collection("Recipe").document(user).collection("Accounts").document("AccountDetails");
            }
        });

    }
}