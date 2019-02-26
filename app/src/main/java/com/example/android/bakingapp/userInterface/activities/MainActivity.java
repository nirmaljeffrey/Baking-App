package com.example.android.bakingapp.userInterface.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.android.bakingapp.util.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeListAdapter;
import com.example.android.bakingapp.idlingResource.BakingAppIdlingResource;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.viewModel.RecipeViewModel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
    RecipeListAdapter.ListItemClickListener {

  private RecyclerView recyclerView;
  private RecipeListAdapter recipeListAdapter;
  private ProgressBar loadingProgressBar;
  private ConstraintLayout errorMessageLayout;
  @Nullable
  private BakingAppIdlingResource appIdlingResource;

  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource() {
    if (appIdlingResource == null) {
      appIdlingResource = new BakingAppIdlingResource();

    }
    return appIdlingResource;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initiateViews();
    getIdlingResource();
    appIdlingResource.setIdleState(false);
    RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
    showProgressBar();
    if (isNetworkAvailable()) {

      recipeViewModel.getRecipesFromNetworkAndStore();
    } else {

      Toast.makeText(this, R.string.recipe_connection_failed, Toast.LENGTH_SHORT).show();
    }

    recipeViewModel.getRecipeListLiveData().observe(this, recipes -> {
      if (recipes != null) {
        showRecipeData();

        recipeListAdapter.setRecipeListData(new ArrayList<>(recipes));
        appIdlingResource.setIdleState(true);
      } else {
        showErrorMessage();

      }
    });
  }

  /**
   * Method used for initiating views in onCreate() method
   */
  private void initiateViews() {
    loadingProgressBar = findViewById(R.id.recipe_list_progress_bar);
    errorMessageLayout = findViewById(R.id.recipe_list_network_error);
    recyclerView = findViewById(R.id.recipe_list_recycler_view);
    recyclerView.setHasFixedSize(true);
    boolean isTablet = getResources().getBoolean(R.bool.isTablet);
    RecyclerView.LayoutManager layoutManager;
    if (isTablet) {
      layoutManager = new GridLayoutManager(MainActivity.this, 3, GridLayoutManager.VERTICAL,
          false);
    } else {
      layoutManager = new GridLayoutManager(MainActivity.this, 1, GridLayoutManager.VERTICAL,
          false);
    }
    recyclerView.setLayoutManager(layoutManager);
    recipeListAdapter = new RecipeListAdapter(this);
    recyclerView.setAdapter(recipeListAdapter);
  }

  /**
   * Method used for hiding error message,recycler view and for showing loading bar
   */
  private void showProgressBar() {
    loadingProgressBar.setVisibility(View.VISIBLE);
    errorMessageLayout.setVisibility(View.GONE);
    recyclerView.setVisibility(View.GONE);
  }

  /**
   * Method used for hiding loading bar,recycler view and for showing error message
   */
  private void showErrorMessage() {
    loadingProgressBar.setVisibility(View.GONE);
    errorMessageLayout.setVisibility(View.VISIBLE);
    recyclerView.setVisibility(View.GONE);
  }

  /**
   * Method used for hiding loading bar, error message and for showing recycler view
   */

  private void showRecipeData() {
    loadingProgressBar.setVisibility(View.GONE);
    errorMessageLayout.setVisibility(View.GONE);
    recyclerView.setVisibility(View.VISIBLE);
  }

  /**
   * Method for checking internet connectivity.
   *
   * @return boolean (internet connection status)
   */

  private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  @Override
  public void onListItemClick(Recipe recipe) {

    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra(Constants.RECIPE_INTENT_EXTRA_CONSTANT, recipe);
    startActivity(intent);
  }
}

