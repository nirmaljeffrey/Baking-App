package com.example.android.bakingapp.userInterface;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepDetailFragment;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepMasterFragment;
import com.example.android.bakingapp.userInterface.fragments.RecipeStepMasterFragment.OnRecipeStepMasterFragmentItemClickListener;


import java.util.ArrayList;

public class RecipeStepMasterActivity extends AppCompatActivity implements OnRecipeStepMasterFragmentItemClickListener{
private ArrayList<RecipeStep> recipeStepArrayList= new ArrayList<>();
private boolean mTwoPane;
private RecipeStep recipeStep;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_step);
    setTitle(R.string.recipe_steps_name);
    Intent intent = getIntent();
    if (intent.getExtras()!=null) {
      recipeStepArrayList = intent.getParcelableArrayListExtra(Constants.RECIPE_STEP_LIST_INTENT_EXTRA_CONSTANT);

    }
    if(findViewById(R.id.two_pane_linear_layout)!=null){
      mTwoPane=true;
      Button nextButton = findViewById(R.id.next_button);
      nextButton.setVisibility(View.GONE);
      Button previousButton = findViewById(R.id.previous_button);
      previousButton.setVisibility(View.GONE);
      if(savedInstanceState==null) {

        RecipeStepDetailFragment fragmentDetail = new RecipeStepDetailFragment();
        fragmentDetail.setRecipeStep(recipeStep);
        fragmentDetail.setRecipeStepArrayList(recipeStepArrayList);
        FragmentTransaction fragmentDetailTransaction = getSupportFragmentManager()
            .beginTransaction();
        fragmentDetailTransaction.add(R.id.recipe_step_detail_fragment_container, fragmentDetail);
        fragmentDetailTransaction.addToBackStack(null);
        fragmentDetailTransaction.commit();
      }

        RecipeStepMasterFragment fragmentMaster = new RecipeStepMasterFragment();
        fragmentMaster.setRecipeStepArrayList(recipeStepArrayList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.recipe_step_master_fragment_container, fragmentMaster);

        fragmentTransaction.commit();

    }else {
      mTwoPane=false;
      RecipeStepMasterFragment fragmentMaster = new RecipeStepMasterFragment();
      fragmentMaster.setRecipeStepArrayList(recipeStepArrayList);
      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      fragmentTransaction.add(R.id.recipe_step_master_fragment_container, fragmentMaster);
      fragmentTransaction.commit();
    }







    }



  @Override
    public void onItemClicked(RecipeStep recipeStep) {
    if(mTwoPane) {
      this.recipeStep = recipeStep;
    }else {
      Intent intent = new Intent(this, RecipeStepDetailActivity.class);
      intent.putParcelableArrayListExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_LIST_INTENT,
          recipeStepArrayList);
      intent.putExtra(Constants.RECIPE_STEP_MASTER_TO_DETAIL_OBJECT_INTENT, recipeStep);
      startActivity(intent);
    }


    }








}
