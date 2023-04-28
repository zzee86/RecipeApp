package com.example.basicversion.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.basicversion.CreatedRecipes;
import com.example.basicversion.R;
import com.example.basicversion.database.LikeDatabase;

import java.util.List;

public class CreatePageAdapter extends ArrayAdapter<CreatedRecipes> {
    LikeDatabase likeDatabase;
    Button like_button;

    public CreatePageAdapter(@NonNull Context context, List<CreatedRecipes> createdRecipes) {
        super(context, 0, createdRecipes);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        CreatedRecipes createdRecipes = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_cell, parent, false);

        likeDatabase = new LikeDatabase(getContext());

        TextView title = convertView.findViewById(R.id.cellTitle);
        TextView cooking = convertView.findViewById(R.id.cellCooking);
        like_button = convertView.findViewById(R.id.like_button_create);
        title.setText(createdRecipes.getTitle());
        cooking.setText(createdRecipes.getCookingTime() + " Minutes");

        this.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition(createdRecipes);
                    CreatedRecipes recipes = CreatedRecipes.createdRecipes.get(position);

                    Log.i("testing", recipes.getTitle() + ", " + recipes.getLikeStatus()+ ", " + position);
                if (recipes.getLikeStatus() == null) {
                    recipes.setLikeStatus("0");
                }

                if (recipes.getLikeStatus().equals("1")){
                    recipes.setLikeStatus("0");
                    likeDatabase.remove_like(String.valueOf(recipes.getId()));
                    Toast.makeText(getContext(),
                            recipes.getTitle() + " has been removed"+ "\n" + "refresh to view changes",
                            Toast.LENGTH_LONG).show();

                    like_button.setBackgroundResource(R.drawable.ic_favourite);
                } else {
                   recipes.setLikeStatus("1");
                    likeDatabase.insertIntoDatabase(String.valueOf(recipes.getId()), recipes.getTitle(), recipes.getInstructions(), recipes.getIngredients(),recipes.getLikeStatus(), recipes.getCookingTime(),recipes.getYield());
                    Toast.makeText(getContext(),
                            recipes.getTitle() + " has been added"+ "\n" + "refresh to view changes",
                            Toast.LENGTH_LONG).show();


                     like_button.setBackgroundResource(R.drawable.ic_baseline_red_heart_24);
                }
            }

        });
        return convertView;
    }

}
