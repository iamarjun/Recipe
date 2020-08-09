package com.arjun.recipe.recipeList

import com.arjun.recipe.model.Recipe

interface Interaction {
    fun onItemSelected(position: Int, item: Recipe)
}
