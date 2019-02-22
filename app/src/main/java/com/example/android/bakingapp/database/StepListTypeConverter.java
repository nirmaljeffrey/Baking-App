package com.example.android.bakingapp.database;

import android.arch.persistence.room.TypeConverter;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.RecipeStep;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StepListTypeConverter {
  @TypeConverter
  public static List<RecipeStep> stringToRecipeStepList(String recipeSteps){
    if(recipeSteps==null){
      return Collections.emptyList();
    }
    Gson gson =new Gson();
    Type listType= new TypeToken<List<Ingredient>>(){}.getType();
    return gson.fromJson(recipeSteps,listType);
  }
  @TypeConverter
  public static String recipeStepListToString(List<RecipeStep> recipeSteps){
    if(recipeSteps==null){
      return null;
    }
    Gson gson = new Gson();
    Type listType= new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(recipeSteps,listType);
  }
}
