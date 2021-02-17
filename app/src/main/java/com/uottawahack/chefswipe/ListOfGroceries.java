package com.uottawahack.chefswipe;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListOfGroceries extends AppCompatActivity {
    ArrayList<String> listOfFood = new ArrayList<>();
    EditText txt;
    ListView show;
    Button saveInput;
    Button clear;
    Button backBtn;
    //TODO: Change into fragment and add to navigation graph and drawer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_groceries);

        txt = (EditText)findViewById(R.id.inputBox);
        show = (ListView)findViewById(R.id.listOfInputtedFood);
        saveInput = (Button)findViewById(R.id.BtnToAdd);
        saveInput.setOnClickListener(v -> {
            String getText = txt.getText().toString();
            if(listOfFood.contains(getText)){
                Toast.makeText(getBaseContext(),"Item Already Added", Toast.LENGTH_LONG).show();
            }
            else if (getText == null || getText.trim().equals("")){
                //do nothing field is empty, thus nothing is to be added to the list
            }
            else{
                listOfFood.add(getText);
                ArrayAdapter<String> adapt = new ArrayAdapter<>(ListOfGroceries.this, android.R.layout.simple_list_item_1, listOfFood);
                show.setAdapter(adapt);
                ((EditText)findViewById(R.id.inputBox)).setText("");
            }
        });
        clear = (Button)findViewById(R.id.ClrBtn);
        clear.setOnClickListener(v ->{
            ArrayAdapter<String> adapt = new ArrayAdapter<>(ListOfGroceries.this, android.R.layout.simple_list_item_1, listOfFood);
            listOfFood.clear();
            show.setAdapter(adapt);

        });
        backBtn = (Button)findViewById(R.id.prevActivity);
        backBtn.setOnClickListener(v -> onBackPressed());
    }
}
