package com.example.basicversion.service;

import com.example.basicversion.model.RecipeList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RandomRecipe {
    @GET("recipes/random?")
    Call<RecipeList> getRecipes(@Query("number") int number, @Query("apiKey") String apiKey, @Query("tags") List<String> tags);
}