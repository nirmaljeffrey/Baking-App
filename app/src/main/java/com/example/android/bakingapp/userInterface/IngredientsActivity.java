package com.example.android.bakingapp.userInterface;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import com.example.android.bakingapp.userInterface.fragments.IngredientFragment;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity{

private ArrayList<Ingredient> ingredientArrayList;

private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent = getIntent();
        if(intent.getExtras()!=null) {

                    ingredientArrayList = intent.getParcelableArrayListExtra(Constants.INGREDIENT_LIST_INTENT_EXTRA_CONSTANT);
                    initiateIngredientFragment();

                }
                }



    private void initiateIngredientFragment(){
        setTitle(R.string.ingredient_name);
        IngredientFragment ingredientFragment = new IngredientFragment();
        ingredientFragment.setIngredientArraylist(ingredientArrayList);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, ingredientFragment);
        fragmentTransaction.commit();
    }

}
