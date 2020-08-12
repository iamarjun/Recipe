package com.arjun.recipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {


    private val _recipeId by lazy { MutableLiveData<String>() }
    private val _isNetworkConnected by lazy { MutableLiveData<Boolean>() }

    val recipeList: LiveData<Resource<List<Recipe>>> = liveData {
        repo.getRecipeList("chicken", _isNetworkConnected.value ?: false).collect {
            emit(it)
        }
    }

    val recipe = _recipeId.switchMap { recipeId ->
        liveData {
            repo.getRecipe(recipeId, _isNetworkConnected.value ?: false).collect {
                emit(it)
            }
        }

    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }

    fun setIsNetworkConnected(isNetworkConnected: Boolean) {
        _isNetworkConnected.value = isNetworkConnected
    }

}