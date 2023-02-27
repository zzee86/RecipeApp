package com.example.basicversion.service;

import com.example.basicversion.model.RecipeList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class RecipeRepository {
    private final RandomRecipe randomRecipeService;
    private  List<String>  tags = new ArrayList<>();

    public RecipeRepository(RandomRecipe recipeService) {
        this.randomRecipeService = recipeService;
    }

    public Call<RecipeList> getListOfRecipes(int number, String apiKey, List<String> tags) {
        return randomRecipeService.getRecipes(number, apiKey, tags);
    }
}