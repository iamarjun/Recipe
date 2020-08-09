package com.arjun.recipe.recipeList

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.arjun.recipe.Resource
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.repositories.RecipeRepository

class RecipeListViewModel @ViewModelInject constructor(private val repo: RecipeRepository) :
    ViewModel() {

    private val _searchQuery by lazy { MutableLiveData<String>() }

    val recipeList: LiveData<Resource<List<Recipe>>> = _searchQuery.switchMap {
        liveData<Resource<List<Recipe>>> {
            emit(Resource.Loading(null))
            try {
                val list = repo.recipeList(it)
                emit(Resource.Success(list))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }
    }

    fun searchRecipe(queryString: String) {
        _searchQuery.value = queryString
    }
}