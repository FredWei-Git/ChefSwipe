package com.uottawahack.chefswipe;

import android.os.Bundle;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CardViewFragment extends Fragment implements View.OnClickListener, View.OnTouchListener{
    private SwipeViewModel vm;
    private MaterialCardViewTouchListener card;
    public CardViewFragment() {
        // Required empty public constructor
        super(R.layout.fragment_card_view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card_view, container, false);
        view.findViewById(R.id.likeButton).setOnClickListener(this);
        view.findViewById(R.id.nextButton).setOnClickListener(this);
        view.findViewById(R.id.infoButton).setOnClickListener(this);

        //Finds SwipeCardView
        card = view.findViewById(R.id.recipeCardView);
        //Adds TouchListener to Card to respond to user swipes
        card.setOnTouchListener(this);
        //Instantiate view model
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Obtain Random ingredient *temp* and calls for recipe using ingredient
        vm.makeIngredientsRequest(false);
        //Update UI when recipeLiveData is changed
        vm.getRecipe().observe(getViewLifecycleOwner(), RecipeInfo -> updateUI(view));
        // Inflate the layout for this fragment
        return view;
    }
    //Updates ui
    private void updateUI(View view) {
        RecipeInfo recipe = vm.getRecipe().getValue();
        TextView recipeName = view.findViewById(R.id.recipeNameView);
        ImageView imageView = view.findViewById(R.id.recipeImageView);
        if (recipe != null) {
            Picasso.get().load(recipe.getImage()).into(imageView);
        }
        if (recipe != null) {
            if (!recipe.getName().equals("")){
                recipeName.setText(recipe.getName());
            }else {
                recipeName.setText(getString(R.string.limitText));
            }
        }
    }
    //Button click listener
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.likeButton) {
            // Changes the food image
            ((MotionLayout) view.getParent()).transitionToEnd();
            vm.makeIngredientsRequest(true);
            ((MotionLayout) view.getParent()).setProgress(0);
        } else if (i == R.id.nextButton) {
            vm.makeIngredientsRequest(false);
        } else if (i == R.id.infoButton) {
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        float previousX = 0;
        float previousY = 0;
        view.onTouchEvent(e);
        view.performClick();
        //Gets x and y at the start of touch
        if (e.getAction() == MotionEvent.ACTION_DOWN){
            previousX = e.getX();
            previousY = e.getY();
        }
        //Calculates change in x and y upon lifting touch
        if (e.getAction() == MotionEvent.ACTION_UP) {
            float x = e.getX();
            float y = e.getY();
            float dx = x - previousX;
            float dy = y - previousY;
            if (dy < -650){
                Log.e("Info", "dy:" + dy);
            } else if (dx > 100) {
                //Animates card and refreshes recipe on swipe
                ((MotionLayout) card.getParent()).transitionToEnd();
                vm.makeIngredientsRequest(true);
                Log.e("Liked", "dx:" + dx);
            } else if (dx < -100){
                vm.makeIngredientsRequest(false);
                Log.e("Passed", "dx:" + dx);
            }

        }
        return true;
    }
}