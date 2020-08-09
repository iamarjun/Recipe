package com.arjun.recipe

import com.arjun.recipe.model.SearchRecipe
import retrofit2.http.GET
import retrofit2.http.Query

interface RestApi {

    @GET("search")
    suspend fun searchRecipe(
        @Query("q") query: String
    ): SearchRecipe
}