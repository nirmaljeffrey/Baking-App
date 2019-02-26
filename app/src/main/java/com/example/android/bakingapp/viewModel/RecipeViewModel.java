package com.example.android.bakingapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.repository.BakingRepository;
import java.util.List;


public class RecipeViewModel extends AndroidViewModel {

  private final BakingRepository bakingRepo;
  private final LiveData<List<Recipe>> recipeList;

  public RecipeViewModel(Application application) {
    super(application);
    bakingRepo = BakingRepository.getInstance(application);
    recipeList = bakingRepo.loadFromDatabase();

  }

  public void getRecipesFromNetworkAndStore() {
    bakingRepo.CallNetworkAndStoreInDb();
  }

  public LiveData<List<Recipe>> getRecipeListLiveData() {
    return recipeList;
  }

}
