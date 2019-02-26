package com.example.android.bakingapp.widget.WidgetUtils;

import android.content.Context;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeView;
import java.util.ArrayList;
import java.util.List;

public final class WidgetUtils {

  /**
   * Method used for obtaining data from the database to display in widgets
   *
   * @param context takes in application context as input parameter
   * @return recipe list to display in widgets
   */
  public static List<Recipe> recipesForWidgets(Context context) {
    List<Recipe> recipeList = new ArrayList<>();
    RecipeDatabase recipeDatabase = RecipeDatabase.getInstance(context);
    List<RecipeView> recipeViews = recipeDatabase.RecipeDao()
        .getRecipesWithIngredientsAndStepsForWidget();
    for (RecipeView recipeView : recipeViews) {
      recipeView.recipe.setIngredients(recipeView.ingredients);
      recipeView.recipe.setSteps(recipeView.recipeSteps);
      recipeList.add(recipeView.recipe);
    }
    return recipeList;
  }
}
