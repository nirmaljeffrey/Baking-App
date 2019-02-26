package com.example.android.bakingapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
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
  abstract void insertRecipes(List<Recipe> recipe);

  @Insert
  abstract void insertIngredients(List<Ingredient> ingredients);

  @Insert
  abstract void insertRecipeSteps(List<RecipeStep> recipeStepList);


  public void insertRecipesWithIngredientsAndSteps(List<Recipe> recipes) {
    for (Recipe recipe : recipes) {
      // loop through ingredients and steps to make sure the recipe id is set before we insert
      List<Ingredient> ingredients = recipe.getIngredients();
      for (Ingredient ingredient : ingredients) {
        ingredient.setRecipeId(recipe.getId());
      }
      insertIngredients(ingredients);
      List<RecipeStep> recipeSteps = recipe.getSteps();
      for (RecipeStep recipeStep : recipeSteps) {
        recipeStep.setRecipeId(recipe.getId());
      }
      insertRecipeSteps(recipeSteps);

    }
    insertRecipes(recipes);
  }

  @Transaction
  @Query("SELECT * FROM recipe_table")
  public abstract LiveData<List<RecipeView>> getRecipesWithIngredientsAndSteps();

  @Transaction
  @Query("SELECT * FROM recipe_table")
  public abstract List<RecipeView> getRecipesWithIngredientsAndStepsForWidget();

  @Query("DELETE FROM recipe_table")
  public abstract void deleteRecipes();

  @Query("DELETE FROM Ingredient")
  public abstract void deleteIngredients();

  @Query("DELETE FROM RecipeStep")
  public abstract void deleteRecipeSteps();

  public void deleteAll() {
    deleteRecipeSteps();
    deleteIngredients();
    deleteRecipes();
  }
}



