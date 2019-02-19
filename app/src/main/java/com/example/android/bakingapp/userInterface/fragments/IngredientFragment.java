package com.example.android.bakingapp.userInterface.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.IngredientListAdapter;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {
    private ArrayList<Ingredient> ingredientArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredient,container,false);
        RecyclerView ingredientRecyclerView = view.findViewById(R.id.ingredient_recycler_view);
        ingredientRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView.setLayoutManager(linearLayoutManager);
        IngredientListAdapter ingredientListAdapter = new IngredientListAdapter(view.getContext());
        ingredientRecyclerView.setAdapter(ingredientListAdapter);
        ingredientListAdapter.setIngredientArrayList(ingredientArrayList);
        return view;
    }
    public void setIngredientArraylist(ArrayList<Ingredient> arraylist){
        this.ingredientArrayList=arraylist;
    }

}
