package com.example.android.bakingapp.userInterface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.android.bakingapp.util.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepDetailFragment;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepMasterFragment;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepMasterFragment.OnRecipeStepMasterFragmentItemClickListener;
import java.util.ArrayList;

public class RecipeStepMasterActivity extends AppCompatActivity implements
    OnRecipeStepMasterFragmentItemClickListener {


  private ArrayList<RecipeStep> recipeStepArrayList = new ArrayList<>();
  private boolean mTwoPane = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_master_step);
    //Actionbar title
    setTitle(R.string.recipe_steps_name);

    Intent intent = getIntent();
    if (intent.getExtras() != null) {
      recipeStepArrayList = intent
          .getParcelableArrayListExtra(Constants.RECIPE_STEP_LIST_INTENT_EXTRA_CONSTANT);

    }
    RecipeStepMasterFragment masterFragment = RecipeStepMasterFragment
        .getMasterFragmentInstance(recipeStepArrayList);
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.add(R.id.recipe_step_master_fragment_container, masterFragment).commit();

    if (findViewById(R.id.two_pane_linear_layout) != null) {
      mTwoPane = true;

      if (savedInstanceState == null) {
        RecipeStepDetailFragment fragmentDetail = RecipeStepDetailFragment
            .getDetailFragmentInstance(recipeStepArrayList, recipeStepArrayList.get(0), true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
            .beginTransaction();
        fragmentTransaction.add(R.id.recipe_step_detail_fragment_container, fragmentDetail);
        fragmentTransaction.commit();


      }
    }


  }


  @Override
  public void onItemClicked(RecipeStep recipeStep) {
    if (mTwoPane) {
      RecipeStepDetailFragment fragmentDetail = RecipeStepDetailFragment
          .getDetailFragmentInstance(recipeStepArrayList, recipeStep, true);
      FragmentTransaction fragmentTransaction = getSupportFragmentManager()
          .beginTransaction();
      fragmentTransaction.replace(R.id.recipe_step_detail_fragment_container, fragmentDetail);
      fragmentTransaction.addToBackStack(null);
      fragmentTransaction.commit();

    } else {

      Intent intent = new Intent(this, RecipeStepDetailActivity.class);
      intent.putParcelableArrayListExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_LIST_INTENT,
          recipeStepArrayList);
      intent.putExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_OBJECT_INTENT, recipeStep);
      startActivity(intent);
    }
  }


}








