package com.example.android.bakingapp.retrofit;

import com.example.android.bakingapp.model.Recipe;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {

  @GET("baking.json")
  Call<ArrayList<Recipe>> getRecipes();
}
