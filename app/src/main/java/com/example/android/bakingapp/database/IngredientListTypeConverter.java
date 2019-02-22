package com.example.android.bakingapp.database;

import android.arch.persistence.room.TypeConverter;
import com.example.android.bakingapp.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


public class IngredientListTypeConverter {

  @TypeConverter
  public static List<Ingredient> stringToIngredientList(String ingredients){
    if(ingredients==null){
      return Collections.emptyList();
    }
   Gson gson =new Gson();
    Type listType= new TypeToken<List<Ingredient>>(){}.getType();
    return gson.fromJson(ingredients,listType);
  }
  @TypeConverter
  public static String ingredientListToString(List<Ingredient> ingredientList){
      if(ingredientList==null){
        return null;
      }
      Gson gson = new Gson();
    Type listType= new TypeToken<List<Ingredient>>(){}.getType();
    return gson.toJson(ingredientList,listType);
  }

}
