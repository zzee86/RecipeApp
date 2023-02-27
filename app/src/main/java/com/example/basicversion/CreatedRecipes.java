package com.example.basicversion;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

public class CreatedRecipes{
    public static ArrayList<CreatedRecipes> createdRecipes = new ArrayList<>();
    public static String RECIPE_EDIT_EXTRA = "recipeEdit";
    private int id;
    private String title;
    private Date date;
    private String likeStatus;
    private String instructions;
    private String ingredients;
    private String cookingTime;
    private String yield;
    private Blob image;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getYield() {
        return yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public CreatedRecipes(int id, String title, String instructions, String ingredients, String likeStatus, String cookingTime, String yield) {
        this.id = id;
        this.title = title;
        this.likeStatus = likeStatus;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
        this.yield = yield;
    }

    public static CreatedRecipes getRecipeID(int passedRecipe) {
        for (CreatedRecipes recipe : createdRecipes){
            if (recipe.getId() == passedRecipe)
                return recipe;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}