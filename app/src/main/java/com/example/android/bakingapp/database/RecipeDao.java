package com.example.android.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.model.RecipeView;
import java.util.List;

@Dao
public abstract class RecipeDao {
  @Insert
  public abstract void _insertRecipes(List<Recipe> recipes);
  @Insert
  public abstract void _insertIngredients(List<Ingredient> ingredients);
  @Insert
  public abstract  void _insertRecipeSteps(List<RecipeStep> recipeSteps);
  public void insertRecipes(List<Recipe> recipes){
    for (Recipe recipe:recipes){
      List<Ingredient> ingredientList = recipe.getIngredients();
      for (Ingredient ingredient:ingredientList){
        ingredient.setRecipeId(recipe.getId());
      }
      _insertIngredients(ingredientList);
      List<RecipeStep> recipeStepList =recipe.getSteps();
      for (RecipeStep recipeStep:recipeStepList){
        recipeStep.setRecipeId(recipe.getId());
      }
      _insertRecipeSteps(recipeStepList);
    }
    _insertRecipes(recipes);
  }
@Transaction
  @Query("SELECT * FROM recipe_table")
  public abstract LiveData<List<RecipeView>> getRecipes();
  @Transaction
  @Query("SELECT * FROM recipe_table WHERE id=:id")
  public abstract RecipeView getRecipe(int id);

}
