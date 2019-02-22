package com.example.android.bakingapp.retrofit;

import android.arch.lifecycle.LiveData;
import com.example.android.bakingapp.model.Recipe;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
