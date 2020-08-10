package com.arjun.recipe.db

import androidx.room.*
import com.arjun.recipe.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeList(list: List<Recipe>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    suspend fun getRecipe(recipeId: String): Recipe

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    fun getRecipeFlow(recipeId: String): Flow<Recipe>

    @Query("select * from recipe")
    fun getRecipeList(): List<Recipe>

    @Query("select * from recipe")
    fun getRecipeListFlow(): Flow<List<Recipe>>

}