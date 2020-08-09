package com.arjun.recipe.repositories

import androidx.paging.PagingSource
import com.arjun.recipe.RestApi
import com.arjun.recipe.model.Recipe
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(private val query: String, private val restApi: RestApi) :
    PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        val position = params.key ?: PAGE
        val apiQuery = query

        return try {

            val response = restApi.searchRecipe(apiQuery, position)
            val list = response.recipes

            LoadResult.Page(
                data = list,
                prevKey = if (position == PAGE) null else position - 1,
                nextKey = if (list.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val PAGE = 1
    }

}