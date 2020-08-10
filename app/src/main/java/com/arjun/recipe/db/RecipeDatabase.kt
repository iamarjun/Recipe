package com.arjun.recipe.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arjun.recipe.model.Recipe

@Database(
    entities = [Recipe::class],
    exportSchema = false,
    version = 5
)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe-db"
    }
}