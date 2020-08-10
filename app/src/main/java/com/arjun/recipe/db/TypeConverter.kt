package com.arjun.recipe.db

import androidx.room.TypeConverter

class RecipeTypeConverter {

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return list?.joinToString()
    }

    @TypeConverter
    fun stringToList(string: String?): List<String>? {
        return string?.split(",")?.map { it.trim() }
    }
}