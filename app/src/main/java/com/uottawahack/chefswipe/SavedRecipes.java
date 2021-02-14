package com.uottawahack.chefswipe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipes extends AppCompatActivity {
    // Variables
    ListView recipesView;
    private final List<String> recipeList = new ArrayList<>();
    private final ArrayList<String> recipeLinks = new ArrayList<>();
    // Firebase
    // firebase
    FirebaseAuth mAuth = FirebaseAuth.getInstance(); //Grabs current instance of database
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_recipes);
        // setting listview
        recipesView = findViewById(R.id.savedRecipesListView);
        // Retrieving data from firestore onto the listview
        String user;
        user = mFirebaseUser.getUid(); //Do what you need to do with the id
        CollectionReference recipeDB = db.collection("users")
                .document(user)
                .collection("SavedRecipes");
        recipeDB.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException error) {
                // clear the lists before you refill them
                recipeList.clear();
                recipeLinks.clear();
                // Loops through th data from firebase
                for (DocumentSnapshot snapshot : value) {
                    String item = snapshot.getString("Recipe Name");
                    recipeList.add(item);
                    recipeLinks.add(snapshot.getString("Recipe Link"));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_selectable_list_item, recipeList);
                adapter.notifyDataSetChanged();
                recipesView.setAdapter(adapter);
            }
        });
        // gives each item in the listView an onclick and then opens the URL
        recipesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // opens url link
                String url = recipeLinks.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}