package com.example.android.bakingapp.userInterface.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.android.bakingapp.util.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.userInterface.fragments.IngredientFragment;
import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

  private ArrayList<Ingredient> ingredientArrayList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ingredients);

    Intent intent = getIntent();
    if (intent.getExtras() != null) {

      ingredientArrayList = intent
          .getParcelableArrayListExtra(Constants.INGREDIENT_LIST_INTENT_EXTRA_CONSTANT);
      initiateIngredientFragment();

    }
  }

  /**
   * Method used for initiating views in onCreate() method
   */

  private void initiateIngredientFragment() {
    setTitle(R.string.ingredient_name);
    IngredientFragment ingredientFragment = new IngredientFragment();
    ingredientFragment.setIngredientArrayList(ingredientArrayList);
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.fragment_container, ingredientFragment);
    fragmentTransaction.commit();
  }

}
