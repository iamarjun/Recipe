package com.arjun.recipe.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.RoundedCornersTransformation
import com.arjun.recipe.R
import com.arjun.recipe.model.Recipe
import kotlinx.android.synthetic.main.recipe_item.view.*

class RecipeListViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader,
    private val interaction: Interaction?
) :
    RecyclerView.ViewHolder(itemView) {

    private var title: AppCompatTextView = itemView.recipe_title
    private var publisher: AppCompatTextView = itemView.recipe_publisher
    private var socialScore: AppCompatTextView = itemView.recipe_social_rating
    private var image: AppCompatImageView = itemView.recipe_image

    fun bind(item: Recipe?) {
        item?.let { recipe ->
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, recipe)
            }

            val request = LoadRequest.Builder(itemView.context)
                .transformations(RoundedCornersTransformation(4f))
                .data(recipe.imageUrl)
                .crossfade(true)
                .target(image)
                .build()

            imageLoader.execute(request)

            title.text = recipe.title
            publisher.text = recipe.publisher
            socialScore.text = recipe.socialRank.toString()
        }
    }

    fun unbind() {
        imageLoader.shutdown()
    }

    companion object {

        fun create(
            parent: ViewGroup,
            imageLoader: ImageLoader,
            interaction: Interaction?
        ): RecipeListViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
            return RecipeListViewHolder(
                view,
                imageLoader,
                interaction
            )
        }
    }
}