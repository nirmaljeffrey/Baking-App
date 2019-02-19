package com.example.android.bakingapp.retrofit;

import com.example.android.bakingapp.model.Recipe;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

    @GET("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> getRecipes();
}
