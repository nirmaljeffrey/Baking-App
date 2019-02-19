package com.example.android.bakingapp.userInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeListAdapter;

import com.example.android.bakingapp.model.Recipe;

import com.example.android.bakingapp.viewModel.RecipeViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeListAdapter recipeListAdapter;
    private RecipeViewModel recipeViewModel;
    private ProgressBar loadingProgressBar;
    private ConstraintLayout errorMessageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initiateViews();
        recipeViewModel=ViewModelProviders.of(this).get(RecipeViewModel.class);
        showProgressBar();

        recipeViewModel.getRecipeListLiveData().observe(this, new Observer<ArrayList<Recipe>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Recipe> recipes) {
                if(recipes!=null) {
                    showRecipeData();
                    recipeListAdapter.setRecipeListData(recipes);
                }else {
                    showErrorMessage();
                }
            }
        });
    }
    private void initiateViews(){
        loadingProgressBar=findViewById(R.id.recipe_list_progress_bar);
        errorMessageLayout= findViewById(R.id.recipe_list_network_error);
        recyclerView = findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recipeListAdapter = new RecipeListAdapter(this);
        recyclerView.setAdapter(recipeListAdapter);
    }
    private void showProgressBar(){
        loadingProgressBar.setVisibility(View.VISIBLE);
        errorMessageLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }
    private void showErrorMessage(){
        loadingProgressBar.setVisibility(View.GONE);
        errorMessageLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
    private void showRecipeData(){
        loadingProgressBar.setVisibility(View.GONE);
        errorMessageLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(Recipe recipe) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(Constants.RECIPE_INTENT_EXTRA_CONSTANT,recipe);
        startActivity(intent);
    }
}
