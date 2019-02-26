package com.example.android.bakingapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import com.example.android.bakingapp.database.AppExecutors;
import com.example.android.bakingapp.database.RecipeDao;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeView;
import com.example.android.bakingapp.retrofit.WebService;
import com.example.android.bakingapp.retrofit.WebServiceGenerator;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingRepository {

  private static final String TAG = "BakingRepository";
  private static BakingRepository bakingRepository;
  private final RecipeDao recipeDao;


  private BakingRepository(Application application) {
    recipeDao = RecipeDatabase.getInstance(application).RecipeDao();

  }

  public static BakingRepository getInstance(Application application) {
    if (bakingRepository == null) {
      bakingRepository = new BakingRepository(application);
    }
    return bakingRepository;
  }

  /**
   * This method is used for getting data from webservice and storing it in room database.
   */
  public void CallNetworkAndStoreInDb() {

    WebService webService = WebServiceGenerator.buildService(WebService.class);
    Call<ArrayList<Recipe>> recipeListCall = webService.getRecipes();
    recipeListCall.enqueue(new Callback<ArrayList<Recipe>>() {
      @Override
      public void onResponse(@NonNull Call<ArrayList<Recipe>> call,
          @NonNull Response<ArrayList<Recipe>> response) {

        if (response.isSuccessful()) {
          if (response.body() != null) {

            List<Recipe> recipes = response.body();

            AppExecutors.getInstance().diskIO().execute(() -> {
              recipeDao.deleteAll();
              recipeDao.insertRecipesWithIngredientsAndSteps(recipes);

            });


          }
        } else {
          Log.i(TAG, "Something went wrong while performing network requests");
        }

      }

      @Override
      public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {

      }
    });

  }

  /**
   * This method is used to retrieve data from the room database
   *
   * @return recipe list for displaying in MainActivity
   */

  public LiveData<List<Recipe>> loadFromDatabase() {
    LiveData<List<RecipeView>> recipeViewsLiveData = recipeDao.getRecipesWithIngredientsAndSteps();
    MediatorLiveData<List<Recipe>> recipesLiveData = new MediatorLiveData<>();
    recipesLiveData.addSource(recipeViewsLiveData, recipeViews -> {
      List<Recipe> recipes = new ArrayList<>();
      for (RecipeView recipeView : recipeViews) {
        recipeView.recipe.setIngredients(recipeView.ingredients);
        recipeView.recipe.setSteps(recipeView.recipeSteps);
        recipes.add(recipeView.recipe);
      }
      recipesLiveData.setValue(recipes);
    });
    return recipesLiveData;
  }


}
