package com.example.basicversion.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.basicversion.service.RecipeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesViewModel extends ViewModel {
    private final MutableLiveData<List<Recipe>> allRecipes;

    public RecipesViewModel() {
        super();
        allRecipes = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        return allRecipes;
    }


    public void requestRandomRecipes(RecipeRepository recipeRepository, List<String> tags) {
        if (allRecipes.getValue().size() == 0) {
            Call<RecipeList> recipeCall = recipeRepository.getListOfRecipes(8, "632ccf930e27490f91099423c847d9d5", tags);
            recipeCall.enqueue(new Callback<RecipeList>() {
                @Override
                public void onResponse(Call<RecipeList> call, Response<RecipeList> response) {
                    if (response.isSuccessful()) {
                        addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<RecipeList> call, Throwable t) {
                    // show error message to user
                    Log.i("AJB", "Error: " + t);
                }
            });
        }
    }

    public void addAll(RecipeList list) {
        allRecipes.getValue().addAll(list.getRecipes());
        allRecipes.setValue(allRecipes.getValue());
    }

}