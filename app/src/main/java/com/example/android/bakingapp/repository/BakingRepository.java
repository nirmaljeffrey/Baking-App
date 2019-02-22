package com.example.android.bakingapp.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.android.bakingapp.database.AppExecutors;
import com.example.android.bakingapp.database.RecipeDao;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.retrofit.WebService;
import com.example.android.bakingapp.retrofit.WebServiceGenerator;

import java.util.ArrayList;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingRepository {
    private static final String TAG = "BakingRepo";
  private RecipeDao recipeDao;
  private static BakingRepository bakingRepository;









    private BakingRepository(Application application) {
      recipeDao = RecipeDatabase.getInstance(application).RecipeDao();

      }
      public static BakingRepository getInstance(Application application){
      if(bakingRepository==null) {
        bakingRepository = new BakingRepository(application);
      }
        return bakingRepository;
      }




    public void CallNetworkAndStoreInDb(){

      WebService webService = WebServiceGenerator.buildService(WebService.class);
        Call<ArrayList<Recipe>> recipeListCall = webService.getRecipes();
        recipeListCall.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {

                if(response.isSuccessful()) {
                    if (response.body() != null) {

                       List<Recipe> recipes= response.body();


                      AppExecutors.getInstance().diskIO().execute(() -> {
                        recipeDao.deleteAll();
                        recipeDao.insertAllRecipes(recipes);
                      });


                        Log.i(TAG, "onResponseMethod entered and response successful");
                    }
                }
                else {
                    Log.i(TAG, "onResponseMethod :  responsefailure");

                }

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {

                Log.i(TAG, "onFailure method entered ");
                Log.e(TAG, " "+ t );

            }
        });

    }





 public LiveData<List<Recipe>> getAllRecipeList(){
      return recipeDao.getRecipesList();
 }









}
