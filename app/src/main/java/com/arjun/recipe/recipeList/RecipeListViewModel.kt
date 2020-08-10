package com.arjun.recipe.recipeList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arjun.recipe.Resource
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository
import kotlinx.coroutines.flow.collect

class RecipeListViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {

    val recipeList: LiveData<Resource<List<Recipe>>> = liveData {
        repo.getRecipeList().collect {
            emit(it)
        }
    }

}