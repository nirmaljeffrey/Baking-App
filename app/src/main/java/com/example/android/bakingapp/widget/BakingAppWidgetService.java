package com.example.android.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.repository.BakingRepository;
import com.example.android.bakingapp.viewModel.RecipeViewModel;
import java.util.ArrayList;

public class BakingAppWidgetService extends RemoteViewsService {

  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new BakingAppViewFactory(getApplicationContext(),intent);
  }
  class BakingAppViewFactory implements RemoteViewsFactory{
    private Context context;
    private int appWidgetId;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    public BakingAppViewFactory(Context context,Intent intent){
      this.context=context;
      this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    private void getRecipeData(){

    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
      return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
      return null;
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
