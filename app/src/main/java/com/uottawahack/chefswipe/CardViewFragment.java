package com.uottawahack.chefswipe;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CardViewFragment extends Fragment implements View.OnClickListener,
        View.OnTouchListener{
    private SwipeViewModel vm;
    private MaterialCardViewTouchListener card;

    //View Pager for CardView
    private ViewPager2 viewPager;
    private static final int NUM_PAGES = 2;

    //Location of first touch
    private float previousX = 0;

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
        //Inflates the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_card_view, container, false);
        //Add click listener to bottom three buttons
        view.findViewById(R.id.likeButton).setOnClickListener(this);
        view.findViewById(R.id.nextButton).setOnClickListener(this);
        view.findViewById(R.id.infoButton).setOnClickListener(this);

        //Adds pages to viewPager
        viewPager = view.findViewById(R.id.cardViewPager);
        FragmentStateAdapter pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //Finds SwipeCardView
        card = view.findViewById(R.id.recipeCardView);
        //Adds TouchListener to Card to respond to user swipes
        card.setOnTouchListener(this);
        //Instantiate view model
        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        //Makes Recipe request
        vm.makeIngredientsRequest(false);

        //Returns inflated view
        return view;
    }

    //Button click listener
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.likeButton) {
            // Changes the food image
            ((MotionLayout) view.getParent()).transitionToEnd();
            vm.makeIngredientsRequest(true);
        } else if (i == R.id.nextButton) {
            vm.makeIngredientsRequest(false);
        } else if (i == R.id.infoButton) {
            if (viewPager.getCurrentItem() == 0){
                viewPager.setCurrentItem(1);
            } else {
                viewPager.setCurrentItem(0);
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        view.onTouchEvent(e);
        view.performClick();
        if (viewPager.getCurrentItem() != 1){
            //Gets x and y at the start of touch
            if (e.getAction() == MotionEvent.ACTION_DOWN){
                previousX = e.getX();
            }
            //Calculates change in x and y upon lifting touch
            if (e.getAction() == MotionEvent.ACTION_UP) {
                float x = e.getX();
                float dx = x - previousX;
                if (dx > 300) {
                    //Animates card and refreshes recipe on swipe
                    ((MotionLayout) card.getParent()).transitionToEnd();
                    vm.makeIngredientsRequest(true);
                    Log.e("Liked", "dx:" + dx);
                } else if (dx < -300){
                    vm.makeIngredientsRequest(false);
                    Log.e("Passed", "dx:" + dx);
                }
            }
            return true;
        }
        //view.dispatchTouchEvent(e);
        return true;
    }


    private static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0){
                return new CardPictureFragment();
            }else {
                return new CardInfoFragment();
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}