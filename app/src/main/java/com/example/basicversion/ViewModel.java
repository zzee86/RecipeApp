package com.example.basicversion;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.basicversion.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ViewModel  extends androidx.lifecycle.ViewModel {
    private final MutableLiveData<List<CreatedRecipes>> allRecipes;

    public ViewModel() {
        super();
        allRecipes = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<CreatedRecipes>> getAllRecipes() {
        Log.i("testings", String.valueOf(allRecipes));
        return allRecipes;

    }

}