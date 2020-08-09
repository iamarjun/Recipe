package com.arjun.recipe.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.arjun.recipe.RestApi
import com.arjun.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val restApi: RestApi) {

    fun recipeList(query: String): Flow<PagingData<Recipe>> {
        Timber.d("New query: $query")
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                RecipePagingSource(
                    query,
                    restApi
                )
            }
        ).flow
    }

}