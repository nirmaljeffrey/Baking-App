package com.example.android.bakingapp.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.repository.BakingRepository;

import java.util.ArrayList;


public class RecipeViewModel extends ViewModel {

    private LiveData<ArrayList<Recipe>> recipeList;
    public RecipeViewModel(){
        recipeList =  BakingRepository.getBakingRepositoryInstance().getRecipeList();

    }
    public LiveData<ArrayList<Recipe>> getRecipeListLiveData(){
        return recipeList;
    }
}
