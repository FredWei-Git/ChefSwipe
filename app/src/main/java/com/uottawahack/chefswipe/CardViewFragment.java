package com.uottawahack.chefswipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;

public class CardViewFragment extends Fragment implements View.OnClickListener {
    private SwipeViewModel vm;

    //View Pager for CardView
    private ViewPager2 viewPager;
    private static final int NUM_PAGES = 2;


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