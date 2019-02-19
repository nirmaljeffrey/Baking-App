package com.example.android.bakingapp.userInterface;

import android.content.Intent;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepDetailFragment;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepDetailFragment.OnRecipeStepDetailFragmentItemClickListener;
import java.util.ArrayList;

public class RecipeStepDetailActivity extends AppCompatActivity implements OnRecipeStepDetailFragmentItemClickListener,OnBackStackChangedListener{
  private ArrayList<RecipeStep> recipeStepArrayList=new ArrayList<>();
  private RecipeStep recipeStep;
  private RecipeStepDetailFragment fragmentDetail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_step_detail);
    Intent intent = getIntent();


    if (intent.getExtras()!=null) {
      recipeStepArrayList = intent.getParcelableArrayListExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_LIST_INTENT);
      recipeStep=intent.getParcelableExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_OBJECT_INTENT);

    }
    getSupportFragmentManager().addOnBackStackChangedListener(this);
    setTitle(R.string.recipe_steps_name);
    if(savedInstanceState!=null){
      fragmentDetail=(RecipeStepDetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,Constants.RECIPE_STEP_DETAIL_FRAGMENT);
    }
    else{
      fragmentDetail = new RecipeStepDetailFragment();
      fragmentDetail.setRecipeStep(recipeStep);
      fragmentDetail.setRecipeStepArrayList(recipeStepArrayList);
      FragmentTransaction fragmentTransaction = getSupportFragmentManager()
          .beginTransaction();
      fragmentTransaction.add(R.id.recipe_step_detail_fragment_container, fragmentDetail);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();
    }
  }

  @Override
  public void previousButtonClicked(int recipeStepId) {


    RecipeStep previousRecipeStep = recipeStepArrayList.get(recipeStepId - 1);

     fragmentDetail = new RecipeStepDetailFragment();
    fragmentDetail.setRecipeStep(previousRecipeStep);
    fragmentDetail.setRecipeStepArrayList(recipeStepArrayList);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.recipe_step_detail_fragment_container, fragmentDetail);
    transaction.addToBackStack(null);
    transaction.commit();








  }

  @Override
  public void nextButtonClicked(int recipeStepId) {

    RecipeStep nextRecipeStep = recipeStepArrayList.get(recipeStepId + 1);
     fragmentDetail = new RecipeStepDetailFragment();
    fragmentDetail.setRecipeStep(nextRecipeStep);
    fragmentDetail.setRecipeStepArrayList(recipeStepArrayList);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.recipe_step_detail_fragment_container, fragmentDetail);
    transaction.addToBackStack(null);
    transaction.commit();
    }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getSupportFragmentManager().putFragment(outState,Constants.RECIPE_STEP_DETAIL_FRAGMENT,fragmentDetail);
  }

  @Override
  public void onBackPressed() {

    if(getSupportFragmentManager().getBackStackEntryCount()>1) {
      getSupportFragmentManager().popBackStack();
    }else if(getSupportFragmentManager().getBackStackEntryCount()==1){

      finish();
      }else {
      super.onBackPressed();
    }
  }

  @Override
  public void onBackStackChanged() {

  }
}
