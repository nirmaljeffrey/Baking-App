package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.widget.WidgetUtils.WidgetUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BakingAppWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new BakingAppViewFactory(getApplicationContext(), intent);
  }

  class BakingAppViewFactory implements RemoteViewsFactory {

    private static final String TAG = "BakingAppViewFactory";
    private Context context;
    private int appWidgetId;
    private List<Recipe> recipeList;
    private List<String> ingredientStringList = new ArrayList<>();
    private List<String> recipeNameList = new ArrayList<>();


    BakingAppViewFactory(Context context, Intent intent) {
      this.context = context;
      this.appWidgetId = intent
          .getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }


    @Override
    public void onDataSetChanged() {
      Log.i(TAG, "onDataSetChanged: ");
      recipeList = WidgetUtils.recipesForWidgets(context);
      for (Recipe recipe : recipeList) {
        recipeNameList.add(recipe.getName());
        List<Ingredient> ingredientListForRecipe = recipe.getIngredients();
        List<String> ingredients = new ArrayList<>();
        for (Ingredient ingredient : ingredientListForRecipe) {
          ingredients.add(String
              .format(Locale.getDefault(), "%s %s %.1f", ingredient.getIngredient(),
                  ingredient.getMeasure(), ingredient.getQuantity()));
        }
        String joinedIngredients = TextUtils.join("\n", ingredients);
        ingredientStringList.add(joinedIngredients);
      }

    }

    @Override
    public void onDestroy() {
      ingredientStringList.clear();
      recipeNameList.clear();
    }

    @Override
    public int getCount() {
      return recipeList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.baking_widget_item);
      remoteViews.setTextViewText(R.id.widget_recipe_title_text_view, recipeNameList.get(position));
      remoteViews.setTextViewText(R.id.widget_ingredient, ingredientStringList.get(position));
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
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }


}
