package com.arjun.recipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {


    private val _recipeId by lazy { MutableLiveData<String>() }
    private val _recipe by lazy { MutableLiveData<String>() }
    private val _isNetworkConnected by lazy { MutableLiveData<Boolean>() }

    val recipeList: LiveData<Resource<List<Recipe>>> = _recipe.switchMap { recipe ->
        _isNetworkConnected.switchMap { isNetworkConnected ->
            liveData {
                repo.getRecipeList(recipe, isNetworkConnected).collect {
                    emit(it)
                }
            }
        }

    }

    val recipe = _recipeId.switchMap { recipeId ->
        _isNetworkConnected.switchMap { isNetworkConnected ->
            liveData {
                repo.getRecipe(recipeId, isNetworkConnected).collect {
                    emit(it)
                }
            }
        }

    }

    fun getRecipes(recipe: String) {
        _recipe.value = recipe
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }

    fun setIsNetworkConnected(isNetworkConnected: Boolean) {
        _isNetworkConnected.value = isNetworkConnected
    }

}