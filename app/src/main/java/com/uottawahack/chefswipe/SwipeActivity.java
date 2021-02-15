package com.uottawahack.chefswipe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class SwipeActivity extends AppCompatActivity {
    //Viewmodel instance
    public SwipeActivity(){
        super(R.layout.activity_swipe);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.logOutButton).setOnClickListener(navClickListener);
        findViewById(R.id.navigationButton).setOnClickListener(navClickListener);

        NavigationView navView = findViewById(R.id.nvView);
        navView.setNavigationItemSelectedListener( item ->{
            NavController navController = Navigation.findNavController(this, R.id.fragment);
            DrawerLayout dView = findViewById(R.id.drawer_layout);
            if (item.getItemId() == R.id.homeItem){
                navController.navigate(R.id.cardViewFragment);
                if (dView.isDrawerOpen(findViewById(R.id.nvView))){
                    dView.close();
                }
            }else if (item.getItemId() == R.id.savedRecipeItem) {
                navController.navigate(R.id.savedRecipes);
                if (dView.isDrawerOpen(findViewById(R.id.nvView))){
                    dView.close();
                }
            } else{
                return false;
            }
            return true;
        });

    }
    private final View.OnClickListener navClickListener = view -> {
        int i = view.getId();
        if (i == R.id.navigationButton){
            DrawerLayout dView = findViewById(R.id.drawer_layout);
            if (!dView.isDrawerOpen(findViewById(R.id.nvView))){
                dView.open();
            }
        }else if (i == R.id.logOutButton){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
        }
    };
}