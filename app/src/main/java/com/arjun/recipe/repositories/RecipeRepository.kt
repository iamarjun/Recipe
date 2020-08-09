package com.arjun.recipe.repositories

import com.arjun.recipe.RestApi
import com.arjun.recipe.model.Recipe
import timber.log.Timber
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val restApi: RestApi) {

    suspend fun recipeList(query: String): List<Recipe> {
        Timber.d("New query: $query")
        val response = restApi.searchRecipe(query)
        return response.recipes
    }

    suspend fun recipeDetail(recipeId: String): Recipe {
        Timber.d("Recipe Id: $recipeId")
        val response = restApi.getRecipe(recipeId)
        return response.recipe
    }

}