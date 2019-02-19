package com.example.android.bakingapp.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;


import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.retrofit.WebService;
import com.example.android.bakingapp.retrofit.WebServiceGenerator;

import java.util.ArrayList;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakingRepository {
    private static final String TAG = "BakingRepo";
    private static BakingRepository bakingRepositoryInstance;
    private WebService webService;
   // constructor made private for singleton pattern
    private BakingRepository(){
        webService = WebServiceGenerator.getRetrofitInstance().create(WebService.class);
    }
  public static BakingRepository getBakingRepositoryInstance(){
        if(bakingRepositoryInstance==null) {
            bakingRepositoryInstance = new BakingRepository();
        }
        return bakingRepositoryInstance;
  }

    public LiveData<ArrayList<Recipe>> getRecipeList(){
        final MutableLiveData<ArrayList<Recipe>> data = new MutableLiveData<>();
        Call<ArrayList<Recipe>> recipeList = webService.getRecipes();
        recipeList.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {

                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        data.setValue(response.body());

                        Log.i(TAG, "onResponseMethod entered and response successful");
                    }
                }
                else {
                    Log.i(TAG, "onResponseMethod :  responsefailure");

                }

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {
                data.setValue(null);
                Log.i(TAG, "onFailure method entered ");
                Log.e(TAG, " "+ t );

            }
        });
        return data;
    }
}
