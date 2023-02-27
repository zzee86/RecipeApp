package com.example.basicversion.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicversion.R;
import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.model.Recipe;

import java.util.List;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {
    Context context;
    List<Recipe> recipes;
    LikeDatabase likeDatabase;

    public LikeAdapter(Context context, List<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_my_list, parent, false);
        likeDatabase = new LikeDatabase(context);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.ViewHolder holder, int position) {
        holder.favouriteTitle.setText(recipes.get(position).title);
        holder.instructions.setText(recipes.get(position).instructions);
        holder.cooking_time.setText(recipes.get(position).readyInMinutes + "Minutes");
        holder.serving.setText(recipes.get(position).servings + "People");
        holder.ingredients.setText(recipes.get(position).testing());
        holder.likeButton.setBackgroundResource(R.drawable.ic_favourite);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView favouriteTitle;
        EditText cooking_time, instructions, ingredients, serving;

        Button likeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favouriteTitle = itemView.findViewById(R.id.favouriteTitle);
            instructions = itemView.findViewById(R.id.edit_instructions);
            cooking_time = itemView.findViewById(R.id.edit_cooking);
            serving = itemView.findViewById(R.id.edit_servings);
            ingredients = itemView.findViewById(R.id.edit_ingredients);
            likeButton = itemView.findViewById(R.id.like_button_create);
        }
    }
}
