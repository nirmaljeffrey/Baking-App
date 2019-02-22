package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import java.util.List;

public class BakingAppWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new BakingAppViewFactory(getApplicationContext(),intent);
  }
  class BakingAppViewFactory implements RemoteViewsFactory{
    private Context context;
    private int appWidgetId;
    private List<Recipe> recipeList;
    private List<String> ingredientQuantity;
    private List<String> ingredientMeasure;
    private List<String> ingredientName;
    private List<String> recipeName;
    private RecipeDatabase ingredientDb;



    public BakingAppViewFactory(Context context,Intent intent){
      this.context=context;
      ingredientDb= RecipeDatabase.getInstance(context);
      this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipeList=ingredientDb.RecipeDao().getRecipesForWidgets();
        for(Recipe recipe:recipeList){
          recipeName.add(recipe.getName());
           List<Ingredient> list = recipe.getIngredients();
          String quantityListString="";
          String measureString="";
          String nameString="";
           for(Ingredient ingredient:list){
             quantityListString+= ingredient.getQuantity().toString() +"\n";
             measureString += ingredient.getMeasure() +"\n";
             nameString+=ingredient.getIngredient()+"\n";



           }
           ingredientQuantity.add(quantityListString);
           ingredientMeasure.add(measureString);
           ingredientName.add(nameString);

        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
      return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
      RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.baking_widget_item);
      remoteViews.setTextViewText(R.id.widget_recipe_title_text_view,recipeName.get(i));
      remoteViews.setTextViewText(R.id.widget_ingredient_name,ingredientName.get(i));
      remoteViews.setTextViewText(R.id.widget_ingredient_measure,ingredientMeasure.get(i));
      remoteViews.setTextViewText(R.id.widget_ingredient_quantity,ingredientQuantity.get(i));

      return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int i) {
      return i;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }
}
