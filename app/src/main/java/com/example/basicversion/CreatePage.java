package com.example.basicversion;

import static com.example.basicversion.CreatedRecipes.createdRecipes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.basicversion.adapter.CreatePageAdapter;
import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.databinding.FragmentCreatePageBinding;
import com.example.basicversion.model.Recipe;
import com.example.basicversion.service.RandomRecipe;
import com.example.basicversion.service.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePage extends Fragment {

    private FragmentCreatePageBinding binding;
    private ListView listView;
    private Button createRecipeBtn;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState

    ) {

        binding = FragmentCreatePageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initWidgets();
        loadDatabase();
        setCreateAdapter();

        createRecipeBtn = view.findViewById(R.id.createRecipeBtn);
        createRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatedRecipeDetail.class);
                startActivity(intent);
                Log.i("createPage", "add button");
            }
        });

        listView = view.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CreatedRecipes selectedRecipe = (CreatedRecipes) listView.getItemAtPosition(position);
                Intent editRecipeIntent = new Intent(getContext(), CreatedPageInformation.class);
                editRecipeIntent.putExtra(CreatedRecipes.RECIPE_EDIT_EXTRA, selectedRecipe.getId());
                startActivity(editRecipeIntent);
            }
        });

    }


        private void loadDatabase() {
        LikeDatabase likeDatabase = new LikeDatabase(getContext());
        likeDatabase.populateRecipeArray();
    }

    private void initWidgets() {
        listView = getView().findViewById(R.id.listView);
    }

    private void setCreateAdapter() {
        CreatePageAdapter createPageAdapter = new CreatePageAdapter(getContext(), createdRecipes);
        listView.setAdapter(createPageAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
