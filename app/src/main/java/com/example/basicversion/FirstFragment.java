package com.example.basicversion;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basicversion.adapter.RecipeListAdapter;
import com.example.basicversion.database.LikeDatabase;
import com.example.basicversion.databinding.FragmentFirstBinding;
import com.example.basicversion.model.Recipe;
import com.example.basicversion.model.RecipeList;
import com.example.basicversion.model.RecipesViewModel;
import com.example.basicversion.service.RandomRecipe;
import com.example.basicversion.service.RecipeRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private RecipeListAdapter mAdapter;
    private RecipesViewModel viewModel;
    private RecyclerView recyclerView;

    private SearchView searchView;
    private Button clear_button;

    Spinner spinner;
    List<String> tags = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(RecipesViewModel.class);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Main");

        final Observer<List<Recipe>> recipeListObserver = new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable final List<Recipe> recipes) {
                mAdapter.updateData(recipes);
            }
        };

        viewModel.getAllRecipes().observe(getViewLifecycleOwner(), recipeListObserver);

        // Get a handle to the RecyclerView.
        recyclerView = view.findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new RecipeListAdapter(getContext(), viewModel.getAllRecipes().getValue());
        // Connect the adapter with the RecyclerView.
        recyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();

        RandomRecipe service = retrofit.create(RandomRecipe.class);

        viewModel.requestRandomRecipes(new RecipeRepository(service), tags);

        clear_button = view.findViewById(R.id.clear_button);

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getQuery() == "hi"){
                    Log.i("testing", "hi");
                }
                tags.clear();
                searchView.setQuery("", false);
                searchView.clearFocus();
                spinner.setSelection(0);
                viewModel.getAllRecipes().getValue().clear();
                viewModel.requestRandomRecipes(new RecipeRepository(service), tags);

            }
        });

        searchView = (SearchView) getView().findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tags.clear();
                tags.add(query);
                viewModel.getAllRecipes().getValue().clear();
                viewModel.requestRandomRecipes(new RecipeRepository(service), tags);
                searchView.clearFocus();
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        spinner = view.findViewById(R.id.spinner_tags);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.tags, R.layout.spinner_text);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(spinnerSelectedItem);
    }

    private final AdapterView.OnItemSelectedListener spinnerSelectedItem = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            tags.clear();
            tags.add(adapterView.getSelectedItem().toString());
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.spoonacular.com/").addConverterFactory(GsonConverterFactory.create()).build();

            RandomRecipe service = retrofit.create(RandomRecipe.class);

            viewModel.getAllRecipes().getValue().clear();
            viewModel.requestRandomRecipes(new RecipeRepository(service), tags);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}