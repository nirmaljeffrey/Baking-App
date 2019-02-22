package com.example.android.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;

@Database(entities = {Recipe.class},version = 1,exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

  public abstract RecipeDao RecipeDao();
  private static RecipeDatabase instance;
  public static synchronized RecipeDatabase getInstance(Context context){
    if(instance==null){
      instance= Room.databaseBuilder(context.getApplicationContext(),RecipeDatabase.class,"recipe_database").build();
    }
    return instance;
  }
}
