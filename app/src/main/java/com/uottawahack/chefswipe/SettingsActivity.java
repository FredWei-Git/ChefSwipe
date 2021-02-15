package com.uottawahack.chefswipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

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
    }


    public void onClick(View view) {
        // check if button got checked
        boolean checked = ((CheckBox) view).isChecked();
        // check which button got checked
        switch (view.getId()) {
            case R.id.balancedSwitch:
                balanced = checked;
                break;
            case R.id.highProteinSwitch:
                highProtein = checked;
                break;
            case R.id.lowFatSwitch:
                lowFat = checked;
                break;
            case R.id.lowCarbSwitch:
                lowCarb = checked;
                break;
            case R.id.veganSwitch:
                vegan = checked;
                break;
            case R.id.vegetarianSwitch:
                vegetarian = checked;
                break;
            case R.id.sugarConsciousSwitch:
                sugarConscious = checked;
                break;
            case R.id.peanutFreeSwitch:
                peanutFree = checked;
                break;
            case R.id.treeNutFreeSwitch:
                treeNutFree = checked;
                break;
            case R.id.alcoholFreeSwitch:
                alcoholFree = checked;
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