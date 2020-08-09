package com.arjun.recipe.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchRecipe(
    @Json(name = "count")
    var count: Int,
    @Json(name = "recipes")
    var recipes: List<Recipe>
)