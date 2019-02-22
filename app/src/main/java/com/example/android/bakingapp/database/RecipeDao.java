package com.example.android.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import java.util.List;

@Dao
public  interface RecipeDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
   void insertAllRecipes(List<Recipe> recipeList);

  @Query("DELETE FROM recipe_table")
  void deleteAll();

  @Query("SELECT * FROM recipe_table")
  List<Recipe> getRecipesForWidgets();

  @Query("SELECT * FROM recipe_table")
  LiveData<List<Recipe>> getRecipesList();



}
