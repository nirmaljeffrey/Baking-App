package com.example.android.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;

@Database(entities = {Recipe.class, RecipeStep.class,
    Ingredient.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

  private static RecipeDatabase instance;

  public static synchronized RecipeDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room
          .databaseBuilder(context.getApplicationContext(), RecipeDatabase.class, "recipe_database")
          .build();
    }
    return instance;
  }

  public abstract RecipeDao RecipeDao();
}
