package com.example.android.bakingapp.userInterface;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;


import android.widget.Toast;
import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeListAdapter;

import com.example.android.bakingapp.model.Recipe;

import com.example.android.bakingapp.viewModel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

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
        if(isNetworkAvailable(this)){
              recipeViewModel.getRecipesFromNetworkAndStore();
        }else {
          Toast.makeText(this, R.string.recipe_connection_failed, Toast.LENGTH_SHORT).show();
        }

        recipeViewModel.getRecipeListLiveData().observe(this, recipes -> {
            if(recipes!=null) {
                showRecipeData();
                recipeListAdapter.setRecipeListData(new ArrayList<>(recipes));
            }else {
              showErrorMessage();
            }
        });
        }
    private void initiateViews(){
        loadingProgressBar=findViewById(R.id.recipe_list_progress_bar);
        errorMessageLayout= findViewById(R.id.recipe_list_network_error);
        recyclerView = findViewById(R.id.recipe_list_recycler_view);
        recyclerView.setHasFixedSize(true);
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if(isTablet) {
          layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL,
              false);
        }else {
          layoutManager=new GridLayoutManager(MainActivity.this,1,GridLayoutManager.VERTICAL,false);
        }
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
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
            = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onListItemClick(Recipe recipe) {

        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
        intent.putExtra(Constants.RECIPE_INTENT_EXTRA_CONSTANT,recipe);
        startActivity(intent);
    }
}
