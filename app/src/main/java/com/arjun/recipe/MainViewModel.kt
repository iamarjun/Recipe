package com.arjun.recipe

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {

    private val _recipeId by lazy { MutableLiveData<String>() }


    val recipeList: LiveData<Resource<List<Recipe>>> = liveData {
        repo.getRecipeList().collect {
            emit(it)
        }
    }

    val recipe = _recipeId.switchMap { recipeId ->
        liveData {
            repo.getRecipe(recipeId).collect {
                emit(it)
            }
        }
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }

}