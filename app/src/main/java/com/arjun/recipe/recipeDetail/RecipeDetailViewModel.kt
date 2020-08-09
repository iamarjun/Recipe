package com.arjun.recipe.recipeDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.arjun.recipe.Resource
import com.arjun.recipe.repositories.RecipeRepository

class RecipeDetailViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {

    private val _recipeId by lazy { MutableLiveData<String>() }

    val recipe = _recipeId.switchMap { recipeId ->
        liveData {
            emit(Resource.Loading(null))
            try {
                emit(Resource.Success(repo.recipeDetail(recipeId)))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }

    fun getRecipe(recipeId: String?) {
        _recipeId.value = recipeId
    }


}