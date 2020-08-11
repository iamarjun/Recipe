package com.arjun.recipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {

    var isNetworkConnected: Boolean = false

    private val _recipeId by lazy { MutableLiveData<String>() }
    private val _recipe by lazy { MutableLiveData<String>() }

    val recipeList: LiveData<Resource<List<Recipe>>> = _recipe.switchMap {
        liveData {
            repo.getRecipeList(it, isNetworkConnected).collect {
                emit(it)
            }
        }
    }

    val recipe = _recipeId.switchMap { recipeId ->
        liveData {
            repo.getRecipe(recipeId, isNetworkConnected).collect {
                emit(it)
            }
        }
    }

    fun getRecipes(recipe: String) {
        _recipe.value = recipe
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }

}