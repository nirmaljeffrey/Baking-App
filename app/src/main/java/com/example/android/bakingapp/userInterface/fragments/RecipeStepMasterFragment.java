package com.example.android.bakingapp.userInterface.fragments;

import android.content.Context;
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
import com.example.android.bakingapp.adapter.RecipeStepMasterAdapter;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.ArrayList;

public class RecipeStepMasterFragment extends Fragment implements RecipeStepMasterAdapter.RecipeStepClickListener {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecipeStepMasterAdapter adapter;
    private OnRecipeStepMasterFragmentItemClickListener fragmentItemClickListener;
        public interface OnRecipeStepMasterFragmentItemClickListener {
            void onItemClicked(RecipeStep recipeStep);
    }

    private ArrayList<RecipeStep> recipeStepArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_step_master,container,false);
        recyclerView=view.findViewById(R.id.master_step_recycler_view);
        linearLayoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new RecipeStepMasterAdapter(this,view.getContext());
        recyclerView.setAdapter(adapter);
        adapter.setRecipeStepArrayList(recipeStepArrayList);
        return view;
    }
    public void setRecipeStepArrayList(ArrayList<RecipeStep> recipeStepArrayList) {
        this.recipeStepArrayList = recipeStepArrayList;
    }


    @Override
    public void onItemClick(RecipeStep recipeStep) {
        fragmentItemClickListener.onItemClicked(recipeStep);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentItemClickListener=(OnRecipeStepMasterFragmentItemClickListener) context;
        }catch (RuntimeException e){
            throw new RuntimeException("Must implement OnRecipeStepMasterFragmentItemClickListener interface");
        }
    }
}
