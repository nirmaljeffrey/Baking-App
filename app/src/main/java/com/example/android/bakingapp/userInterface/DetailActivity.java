package com.example.android.bakingapp.userInterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<Ingredient> ingredientArrayList ;
    private List<RecipeStep> recipeStepArrayList;

    private String recipeName;
    private String recipeServings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        if(intent!=null){
            Recipe recipe = intent.getParcelableExtra(Constants.RECIPE_INTENT_EXTRA_CONSTANT);

              ingredientArrayList = new ArrayList<>(recipe.getIngredients());
              recipeStepArrayList = new ArrayList<>(recipe.getSteps());
              recipeName = recipe.getName();
              recipeServings = String.valueOf(recipe.getServings());


        }

        setTitle(recipeName);
        CardView ingredientCardView = findViewById(R.id.ingredient_card);
        CardView recipeStepCardView = findViewById(R.id.recipe_step_card);
        TextView ingredientBodyTextView = findViewById(R.id.ingredients_body);
        TextView recipeStepBodyTextView = findViewById(R.id.recipe_steps_body);

        final String ingredientBodyString = String.format(getResources().getString(R.string.ingredient_body),recipeName,recipeServings);
        String recipeStepBodyString = String.format(getResources().getString(R.string.recipe_step_body),recipeName,recipeName);
        ingredientBodyTextView.setText(ingredientBodyString);
        recipeStepBodyTextView.setText(recipeStepBodyString);

        ingredientCardView.setOnClickListener(view -> {
            ArrayList<Ingredient> ingredients=new ArrayList<>(ingredientArrayList);
            Intent ingredientIntent = new Intent(DetailActivity.this,IngredientsActivity.class);
            ingredientIntent.putParcelableArrayListExtra(Constants.INGREDIENT_LIST_INTENT_EXTRA_CONSTANT,ingredients);
            startActivity(ingredientIntent);
        });
        recipeStepCardView.setOnClickListener(view -> {
            ArrayList<RecipeStep> recipeSteps = new ArrayList<>(recipeStepArrayList);
            Intent recipeStepIntent = new Intent(DetailActivity.this,RecipeStepMasterActivity.class);
            recipeStepIntent.putParcelableArrayListExtra(Constants.RECIPE_STEP_LIST_INTENT_EXTRA_CONSTANT,recipeSteps);
            startActivity(recipeStepIntent);
        });



    }
}
