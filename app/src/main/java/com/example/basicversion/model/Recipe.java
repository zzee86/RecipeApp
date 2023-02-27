package com.example.basicversion.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public boolean lowFodmap;
    public int weightWatcherSmartPoints;
    public String gaps;
    public int preparationMinutes;
    public int cookingMinutes;
    public int aggregateLikes;
    public int healthScore;
    public String creditsText;
    public String license;
    public String sourceName;
    public double pricePerServing;
    public ArrayList<ExtendedIngredient> extendedIngredients;
    public int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public String image;
    public String imageType;
    public String summary;
    public ArrayList<Object> cuisines;
    public ArrayList<String> dishTypes;
    public ArrayList<String> diets;
    public ArrayList<String> occasions;
    public String instructions;
    public ArrayList<AnalyzedInstruction> analyzedInstructions;
    public Object originalId;
    public String spoonacularSourceUrl;

    public String key_id;
    public String likeStatus;

    public Recipe(String title, String id) {

    }

    public String getKey_id() {
        return key_id;
    }

    public void setKey_id(String key_id) {
        this.key_id = key_id;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public String setLikeStatus(String likeStatus) {
        return this.likeStatus = likeStatus;
    }


    public ArrayList<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(ArrayList<AnalyzedInstruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }


    public String replaceAll(String regex, String replacement) {
        return null;
    }


    public StringBuilder testing() {
        StringBuilder sb = new StringBuilder();

        for (AnalyzedInstruction var : getAnalyzedInstructions()) {
            ArrayList<Step> steps = var.getSteps();
            for (Step s : steps) {
                ArrayList<Ingredient> ingredients = s.ingredients;
                for (Ingredient i : ingredients) {
                    sb.append(i.name + "\n");
                }
            }
        }
        return sb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title);

        for (AnalyzedInstruction var : getAnalyzedInstructions()) {
            ArrayList<Step> steps = var.getSteps();
            for (Step s : steps) {
                sb.append(s.step + "\n");
            }
        }

        return sb.toString();
    }
}

class AnalyzedInstruction {
    public String name;
    public ArrayList<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}

class Equipment {
    public int id;
    public String name;
    public String localizedName;
    public String image;
}

class ExtendedIngredient {
    public int id;
    public String aisle;
    public String image;
    public String consistency;
    public String name;
    public String nameClean;
    public String original;
    public String originalName;
    public double amount;
    public String unit;
    public ArrayList<String> meta;
    public Measures measures;
}

class Length {
    public int number;
    public String unit;
}

class Measures {
    public Us us;
    public Metric metric;
}

class Metric {
    public double amount;
    public String unitShort;
    public String unitLong;
}

class Step {
    public int number;
    public String step;
    public ArrayList<Ingredient> ingredients;
    public ArrayList<Equipment> equipment;
    public Length length;
}

class Us {
    public double amount;
    public String unitShort;
    public String unitLong;
}

class Ingredient {
    public int id;
    public String name;
    public String localizedName;
    public String image;


}
