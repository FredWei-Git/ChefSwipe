package com.uottawahack.chefswipe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipeFragment extends Fragment {
    // Variables
    private SwipeViewModel vm;
    ListView recipesView;

    public SavedRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_recipe, container, false);
        recipesView = view.findViewById(R.id.savedRecipesListView);

        vm = new ViewModelProvider(this).get(SwipeViewModel.class);
        vm.makeRecipeListRequest();
        vm.getRecipeList().observe(getViewLifecycleOwner(), this::updateUI);
        return view;
    }
    public void updateUI(List<String> string) {
        ArrayList<String> recipeLinks = vm.getRecipeLinks().getValue();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_selectable_list_item, string);
        adapter.notifyDataSetChanged();
        recipesView.setAdapter(adapter);
        // gives each item in the listView an onclick and then opens the URL
        recipesView.setOnItemClickListener((parent, view1, position, id) -> {
            // opens url link
            try{
                String url = recipeLinks.get(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }catch (NullPointerException e){
                Log.e("Recipe Links", "updateUI: No Links");
            }
        });
    }
}