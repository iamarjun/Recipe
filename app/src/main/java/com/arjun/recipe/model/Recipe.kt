package com.arjun.recipe.model

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipe(
    @PrimaryKey
    @Json(name = "_id")
    var id: String,
    @Json(name = "image_url")
    var imageUrl: String,
    @Json(name = "ingredients")
    var ingredients: List<String>?,
    @Json(name = "publisher")
    var publisher: String,
    @Json(name = "publisher_url")
    var publisherUrl: String,
    @Json(name = "recipe_id")
    var recipeId: String,
    @Json(name = "social_rank")
    var socialRank: Float,
    @Json(name = "source_url")
    var sourceUrl: String,
    @Json(name = "title")
    var title: String
)