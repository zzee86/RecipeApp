package com.example.basicversion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.model.Recipe;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.basicversion.databinding.ActivityCreatedRecipeDetailBinding;

import java.util.ArrayList;

public class CreatedRecipeDetail extends AppCompatActivity {

    private CreatedRecipes selectedRecipe;
    private EditText titleEditText, edit_cooking, edit_serving, edit_instructions, edit_ingredients;
    private Button back_btn_recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_recipe_detail);
        initWidgets();
        checkForEditRecipe();

        back_btn_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void checkForEditRecipe() {
        Intent previousIntent = getIntent();
        int passedRecipe = previousIntent.getIntExtra(CreatedRecipes.RECIPE_EDIT_EXTRA, -1);
        selectedRecipe = CreatedRecipes.getRecipeID(passedRecipe);

        if (selectedRecipe != null) {
            titleEditText.setText(selectedRecipe.getTitle());
            edit_instructions.setText(selectedRecipe.getInstructions());
            edit_ingredients.setText(selectedRecipe.getIngredients());
            edit_cooking.setText(selectedRecipe.getCookingTime());
            edit_serving.setText(selectedRecipe.getYield());
            Log.i("cooking", "check for edit: "+ selectedRecipe.getCookingTime());

        }

    }


    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        edit_instructions = findViewById(R.id.edit_instructions);
        edit_ingredients = findViewById(R.id.edit_ingredients);
        edit_cooking = findViewById(R.id.edit_cooking);
        edit_serving = findViewById(R.id.edit_servings);
        back_btn_recipes = findViewById(R.id.back_btn_recipes);
    }

    public void saveRecipe(View view) {
        LikeDatabase likeDatabase = new LikeDatabase(this);

        String title = String.valueOf(titleEditText.getText());
        String instruction = String.valueOf(edit_instructions.getText());
        String ingredients = String.valueOf(edit_ingredients.getText());
        String cooking = String.valueOf(edit_cooking.getText());
        String serving = String.valueOf(edit_serving.getText());
        String like = "0";
        Log.i("cooking", "save: " + cooking);


        if (selectedRecipe == null) {
            int id = CreatedRecipes.createdRecipes.size();
            CreatedRecipes recipes = new CreatedRecipes(id, title, instruction, ingredients, like, cooking, serving);
            CreatedRecipes.createdRecipes.add(recipes);
            likeDatabase.addRecipe(recipes);
            Log.i("cooking", "if null: "+selectedRecipe.getCookingTime());

        } else {
            selectedRecipe.setTitle(title);
            selectedRecipe.setInstructions(instruction);
            selectedRecipe.setIngredients(ingredients);
            selectedRecipe.setCookingTime(cooking);
            selectedRecipe.setYield(serving);
            selectedRecipe.setLikeStatus(like);

            Log.i("cooking", "else: " + cooking);
            Log.i("cooking", "else selected: " +selectedRecipe.getCookingTime());

            likeDatabase.updateRecipe(selectedRecipe);
        }
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
    }

}