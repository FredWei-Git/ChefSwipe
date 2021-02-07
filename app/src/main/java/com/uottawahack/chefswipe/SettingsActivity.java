package com.uottawahack.chefswipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox balancedSwitch, highProteinSwitch, lowFatSwitch, lowCarbSwitch, veganSwitch, vegetarianSwitch,
            sugarConsciousSwitch, peanutFreeSwitch, treeNutFreeSwitch, alcoholFreeSwitch;
    private boolean balanced, highProtein, lowFat, lowCarb, vegan, vegetarian,
            sugarConscious, peanutFree, treeNutFree, alcoholFree = false;

    Button savedRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        // balanced, high protein, low-fat, low-carb, vegan, vegetarian, sugar-conscious, peanut-free, tree-nut-free, alcohol-free
        // go to saved recipe page
        savedRecipes = (Button) findViewById(R.id.savedRecipesButton);
        savedRecipes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //
                Intent intent = new Intent(getApplicationContext(), SavedRecipes.class);
                startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        // check if button got checked
        boolean checked = ((CheckBox) view).isChecked();
        // check which button got checked
        switch (view.getId()) {
            case R.id.balancedSwitch:
                if (checked)
                    balanced = true;
                else
                    balanced = false;
                break;
            case R.id.highProteinSwitch:
                if (checked)
                    highProtein = true;
                else
                    highProtein = false;
                break;
            case R.id.lowFatSwitch:
                if (checked)
                    lowFat = true;
                else
                    lowFat = false;
                break;
            case R.id.lowCarbSwitch:
                if (checked)
                    lowCarb = true;
                else
                    lowCarb = false;
                break;
            case R.id.veganSwitch:
                if (checked)
                    vegan = true;
                else
                    vegan = false;
                break;
            case R.id.vegetarianSwitch:
                if (checked)
                    vegetarian = true;
                else
                    vegetarian = false;
                break;
            case R.id.sugarConsciousSwitch:
                if (checked)
                    sugarConscious = true;
                else
                    sugarConscious = false;
                break;
            case R.id.peanutFreeSwitch:
                if (checked)
                    peanutFree = true;
                else
                    peanutFree = false;
                break;
            case R.id.treeNutFreeSwitch:
                if (checked)
                    treeNutFree = true;
                else
                    treeNutFree = false;
                break;
            case R.id.alcoholFreeSwitch:
                if (checked)
                    alcoholFree = true;
                else
                    alcoholFree = false;
                break;
        }
    }


//        }
//        findViewById(R.id.balancedSwitch).setOnClickListener(this);
//        findViewById(R.id.highProteinSwitch).setOnClickListener(this);
//        findViewById(R.id.lowFatSwitch).setOnClickListener(this);
//        findViewById(R.id.lowCarbSwitch).setOnClickListener(this);
//        findViewById(R.id.veganSwitch).setOnClickListener(this);
//        findViewById(R.id.vegetarianSwitch).setOnClickListener(this);
//        findViewById(R.id.sugarConsciousSwitch).setOnClickListener(this);
//        findViewById(R.id.peanutFreeSwitch).setOnClickListener(this);
//        findViewById(R.id.treeNutFreeSwitch).setOnClickListener(this);
//        findViewById(R.id.alcoholFreeSwitch).setOnClickListener(this);


}