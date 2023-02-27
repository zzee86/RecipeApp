package com.example.basicversion;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.basicversion.databinding.ActivityCreatedPageInformationBinding;

public class CreatedPageInformation extends AppCompatActivity {

    private CreatedRecipes selectedRecipe;
    private TextView titleEditText, edit_cooking, edit_serving, edit_instructions, edit_ingredients;
    private LinearLayout back_btn_recipes_linear;
    private Button back_btn_recipes, editRecipe;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_page_information);

        initWidgets();
        checkForEditRecipe();



        back_btn_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editRecipeIntent = new Intent(getApplicationContext(), CreatedRecipeDetail.class);
                editRecipeIntent.putExtra(CreatedRecipes.RECIPE_EDIT_EXTRA, selectedRecipe.getId());

                overridePendingTransition(0, 0);
                startActivity(editRecipeIntent);
                overridePendingTransition(0, 0);
            }
        });


    }

    private void initWidgets() {
        titleEditText = findViewById(R.id.titleEditText);
        edit_instructions = findViewById(R.id.edit_instructions);
        edit_ingredients = findViewById(R.id.edit_ingredients);
        edit_cooking = findViewById(R.id.edit_cooking);
        edit_serving = findViewById(R.id.edit_servings);
        back_btn_recipes_linear = findViewById(R.id.back_btn_recipes_linear);
        back_btn_recipes = findViewById(R.id.back_btn_recipes);
        editRecipe = findViewById(R.id.editRecipe);

    }

    private void checkForEditRecipe() {
        Intent previousIntent = getIntent();
        int passedRecipe = previousIntent.getIntExtra(CreatedRecipes.RECIPE_EDIT_EXTRA, -1);
        selectedRecipe = CreatedRecipes.getRecipeID(passedRecipe);

        if (selectedRecipe != null) {
            titleEditText.setText(selectedRecipe.getTitle());
            edit_instructions.setText(selectedRecipe.getInstructions());
            edit_ingredients.setText(selectedRecipe.getIngredients());
            edit_cooking.setText(selectedRecipe.getCookingTime() + " Minutes");
            edit_serving.setText(selectedRecipe.getYield() + " Servings");
        }

    }

}