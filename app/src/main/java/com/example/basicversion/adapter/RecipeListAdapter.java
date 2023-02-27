package com.example.basicversion.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicversion.CreatedRecipes;
import com.example.basicversion.FirstFragmentDirections;
import com.example.basicversion.R;
import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.model.Recipe;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    List<Recipe> recipes;
    private final LayoutInflater mInflater;
    LikeDatabase likeDatabase;
    Context context;

    public RecipeListAdapter(Context context, List<Recipe> list) {
        this.mInflater = LayoutInflater.from(context);
        this.recipes = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        likeDatabase = new LikeDatabase(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", MODE_PRIVATE);

        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTable0nFirstStart();
        }

        View mItemView = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(mItemView, this);


    }

    private void createTable0nFirstStart() {
        likeDatabase.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        //   holder.titleView.setText(recipes.get(position).title);

        Recipe recipe = recipes.get(position);
        holder.recipe = recipe;

        TextView textView = holder.titleView;
        textView.setText(recipe.title);

        // Image
        String thumb = recipe.image;
        Picasso.get().load(thumb).into(holder.recipeImage);

        // Cooking Minutes
        holder.textView_cooking.setText(recipes.get(position).readyInMinutes + " Minutes");

    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateData(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView titleView;
        public final ImageView recipeImage;
        public ImageButton like_button;
        public final TextView textView_cooking;
        final RecipeListAdapter mAdapter;
        public Recipe recipe;

        public RecipeViewHolder(@NonNull View itemView, RecipeListAdapter adapter) {
            super(itemView);
            itemView.setOnClickListener(this);
            titleView = itemView.findViewById(R.id.titleView);
            this.recipeImage = itemView.findViewById(R.id.recipeimage);
            this.like_button = itemView.findViewById(R.id.like_button);
            textView_cooking = itemView.findViewById(R.id.textView_cooking);
            this.mAdapter = adapter;


            //  CreatedRecipes recipe = CreatedRecipes.createdRecipes.get(getAdapterPosition());

            //      Log.i("testing", String.valueOf(recipe.id));
            //    Cursor temp = likeDatabase.likedItem(recipe.key_id);

//            while (temp.moveToNext()) {
//                int id = temp.getInt(1);
//                Log.i("testing", String.valueOf(id));
//
//                if (id >= 3) {
//                    like_button.setBackgroundResource(R.drawable.ic_baseline_red_heart_24);
//                    Log.i("testing", "its 0");
//
//                } else {
//                    Log.i("testing", "its more");
//
//                }
//            }

            this.like_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Recipe recipe = recipes.get(position);

                    if (recipe.likeStatus == null) {
                        recipe.setLikeStatus("0");
                    }

                    if (recipe.getLikeStatus().toString() == "0") {
                        recipe.setLikeStatus("1");
                        likeDatabase.insertIntoDatabase(String.valueOf(recipe.id), recipe.title, recipe.instructions, String.valueOf(recipe.testing()), recipe.getLikeStatus(), String.valueOf(recipe.readyInMinutes), String.valueOf(recipe.servings));
                        like_button.setBackgroundResource(R.drawable.ic_baseline_red_heart_24);
                        Log.i("testing11", "working");
                    } else {
                        recipe.setLikeStatus("0");
                        likeDatabase.remove_like(String.valueOf(recipe.id));
                        like_button.setBackgroundResource(R.drawable.ic_favourite);
                    }
                }
            });
        }




        @Override
        public void onClick(View v) {
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(recipe);
            Navigation.findNavController(v).navigate(action);
        }
    }
}


