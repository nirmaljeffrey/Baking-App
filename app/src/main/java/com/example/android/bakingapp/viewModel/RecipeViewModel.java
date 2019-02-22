package com.example.android.bakingapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.repository.BakingRepository;

import java.util.ArrayList;
import java.util.List;


public class RecipeViewModel extends AndroidViewModel {
    private BakingRepository bakingRepo;
    private LiveData<List<Recipe>> recipeList;

    public RecipeViewModel(Application application){
        super(application);
        bakingRepo=BakingRepository.getInstance(application);
        recipeList=bakingRepo.getAllRecipeList();

    }
    public void getRecipesFromNetworkAndStore(){
      bakingRepo.CallNetworkAndStoreInDb();
    }
    public LiveData<List<Recipe>> getRecipeListLiveData(){
        return recipeList;
    }

}
