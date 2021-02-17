package com.uottawahack.chefswipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class CardInfoFragment extends Fragment {
    //View Model of CardView Fragments
    private SwipeViewModel vm;

    public CardInfoFragment() {
        // Required empty public constructor
        super(R.layout.fragment_card_info);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_info, container, false);
        ImageView attributionImage = view.findViewById(R.id.attributions);
        attributionImage.setBackgroundResource(R.drawable.badge);
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Call UpdateUI when observed LiveData RecipeInfo has changed
        vm.getRecipe().observe(getViewLifecycleOwner(), RecipeInfo -> updateUI(RecipeInfo, view));
        //Returns the inflated view
        return view;
    }
    //Updates UI when observed Data is changed
    private void updateUI(RecipeInfo recipe, View view) {
        ListView ingredientsList = view.findViewById(R.id.ingredientsList);
        ingredientsList.requestDisallowInterceptTouchEvent(true);
        //Contains the list of ingredients
        ArrayList<String> ingredients = recipe.getIngredients();
        //Array Adapter
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1, ingredients);
        listAdapter.notifyDataSetChanged();
        ingredientsList.setAdapter(listAdapter);

    }

}