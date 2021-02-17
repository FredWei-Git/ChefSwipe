package com.uottawahack.chefswipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CardPictureFragment extends Fragment {
    //View Model of CardView Fragments
    private SwipeViewModel vm;
    public CardPictureFragment() {
        // Required empty public constructor
        super(R.layout.fragment_card_picture);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_picture, container, false);
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Observes changes in UI Data
        vm.getRecipe().observe(getViewLifecycleOwner(), RecipeInfo -> updateUI(RecipeInfo, view));
        //Returns inflated view
        return view;
    }
    //Updates ui
    private void updateUI(RecipeInfo recipe, View view) {
        TextView recipeName = view.findViewById(R.id.recipeNameView);
        ImageView imageView = view.findViewById(R.id.recipeImageView);
        if (recipe != null && imageView != null) {
            Picasso.get().load(recipe.getImage()).into(imageView);
            if (!recipe.getName().equals("")){
                recipeName.setText(recipe.getName());
            }else {
                recipeName.setText(getString(R.string.limitText));
            }
        }
    }
}