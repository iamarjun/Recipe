package com.arjun.recipe.db

import androidx.room.*
import com.arjun.recipe.model.Recipe

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeList(list: List<Recipe>)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("select * from recipe where recipeId like '%'||:recipeId||'%'")
    suspend fun getRecipe(recipeId: String): Recipe

    @Query("select * from recipe where lower(title) like '%'||:query||'%' order by socialRank desc")
    fun getRecipeList(query: String): List<Recipe>

}