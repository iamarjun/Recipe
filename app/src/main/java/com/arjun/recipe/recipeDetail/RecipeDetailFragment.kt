package com.arjun.recipe.recipeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import coil.request.LoadRequest
import coil.transform.RoundedCornersTransformation
import com.arjun.recipe.MainViewModel
import com.arjun.recipe.R
import com.arjun.recipe.Resource
import com.arjun.recipe.base.BaseFragment
import com.arjun.recipe.databinding.FragmentRecipeDetailBinding
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.util.viewBinding
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RecipeDetailFragment : BaseFragment() {

    private val binding: FragmentRecipeDetailBinding by viewBinding(FragmentRecipeDetailBinding::bind)
    private val args: RecipeDetailFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var title: MaterialTextView
    private lateinit var publisherName: MaterialTextView
    private lateinit var backdrop: ImageView
    private lateinit var ingredients: MaterialTextView
    private lateinit var loader: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")

        viewModel.getRecipe(args.recipeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        Timber.d(args.recipeId)

        title = binding.title
        backdrop = binding.backdrop
        publisherName = binding.publisherName
        ingredients = binding.recipeIngredients
        loader = binding.loader

        viewModel.recipe.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading -> {
                    loader.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    loader.visibility = View.GONE
                    showRecipe(it.data)
                }
                is Resource.Error -> {
                    loader.visibility = View.GONE
                }
            }
        }

    }

    private fun showRecipe(recipe: Recipe?) {
        title.text = recipe?.title
        ingredients.text = recipe?.ingredients?.joinToString(separator = "\n")
        publisherName.text = "By: ${recipe?.publisher}"

        val request = LoadRequest.Builder(requireContext())
            .transformations(RoundedCornersTransformation(4f))
            .data(recipe?.imageUrl)
            .crossfade(true)
            .target(backdrop)
            .build()

        imageLoader.execute(request)
    }
}