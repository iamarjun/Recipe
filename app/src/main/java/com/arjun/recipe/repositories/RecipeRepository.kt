package com.arjun.recipe.repositories

import com.arjun.recipe.Resource
import com.arjun.recipe.RestApi
import com.arjun.recipe.db.RecipeDatabase
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.networkBoundResource
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val restApi: RestApi,
    private val db: RecipeDatabase
) {

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

    fun getRecipeList(): Flow<Resource<List<Recipe>>> = networkBoundResource(
        query = { db.recipeDao.getRecipeListFlow() },
        fetch = { restApi.searchRecipe("chicken") },
        saveFetchResult = { item -> db.recipeDao.insertRecipeList(item.recipes) }
    )

    fun getRecipe(recipeId: String): Flow<Resource<Recipe>> = networkBoundResource(
        query = { db.recipeDao.getRecipeFlow(recipeId) },
        fetch = { restApi.getRecipe(recipeId) },
        saveFetchResult = { item -> db.recipeDao.updateRecipe(item.recipe) }
    )

}