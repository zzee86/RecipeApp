package com.example.basicversion;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.basicversion.adapter.RecipeListAdapter;
import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.databinding.ActivityMainBinding;
import com.example.basicversion.databinding.FragmentSecondBinding;
import com.example.basicversion.model.Recipe;
import com.example.basicversion.model.RecipeList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Recipe recipe;
    LikeDatabase likeDatabase;

    Button like_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recipe = SecondFragmentArgs.fromBundle(getArguments()).getRecipe();

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.textviewSecond.setText(recipe.title);

        // Image
        String thumb = recipe.image;
        Picasso.get().load(thumb).into(binding.imageView);

        binding.textViewCooking.setText(recipe.readyInMinutes + " Minutes");
        binding.textViewServings.setText(recipe.servings + " Servings");
        //  binding.textviewSummary.setText(recipe.summary.replaceAll("<.*?>", ""));

        binding.textViewInstructions.setText(Html.fromHtml(recipe.instructions));
        binding.textViewIngredients.setText(recipe.testing());


        if (recipe.getLikeStatus() == null) {
            binding.likeButton.setBackgroundResource(R.drawable.ic_favourite);
        } else {
            binding.likeButton.setBackgroundResource(R.drawable.ic_baseline_red_heart_24);

        }

/*
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (recipe.likeStatus == null) {
                    recipe.setLikeStatus("0");

                    Log.i("like button", "status set to: " + recipe.getLikeStatus());
                }

                if (recipe.getLikeStatus().toString() == "0") {
                    recipe.setLikeStatus("1");
                    Log.i("like button", "status set to: " + recipe.getLikeStatus());

                    likeDatabase.insertIntoDatabase(String.valueOf(recipe.id), recipe.title, recipe.instructions, String.valueOf(recipe.testing()),recipe.getLikeStatus(), recipe.readyInMinutes + " Minutes",recipe.servings + " Servings");
                    binding.likeButton.setBackgroundResource(R.drawable.ic_baseline_red_heart_24);
                    Log.i("likedb", "1");

                } else {
                    recipe.setLikeStatus("0");
                    Log.i("like button", "status set to: " + recipe.getLikeStatus());

                 //   likeDatabase.remove_like(String.valueOf(recipe.id));
                    binding.likeButton.setBackgroundResource(R.drawable.ic_favourite);
                    Log.i("likedb", recipe.getLikeStatus());
                }


            }
        });
*/


        binding.backBtnRecipesLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        binding.backBtnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}