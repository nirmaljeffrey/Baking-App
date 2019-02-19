package com.example.android.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;

@Database(entities = {Recipe.class,Ingredient.class,RecipeStep.class},version = 1,exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

  public abstract RecipeDao recipeDao();
}
