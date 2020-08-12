package com.arjun.recipe.recipeList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.recipe.MainViewModel
import com.arjun.recipe.R
import com.arjun.recipe.Resource
import com.arjun.recipe.base.BaseFragment
import com.arjun.recipe.databinding.FragmentRecipeListBinding
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {

    private val binding: FragmentRecipeListBinding by viewBinding(FragmentRecipeListBinding::bind)

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recipeAdapter: RecipeListAdapter
    private lateinit var recipeList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var notFound: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")

        recipeList = binding.recipeList
        progressBar = binding.progressBar
        notFound = binding.notFound

        recipeAdapter = RecipeListAdapter(imageLoader, object : Interaction {
            override fun onItemSelected(position: Int, item: Recipe) {
                Timber.d("${item.title} at $position")
                val action =
                    RecipeListFragmentDirections.actionRecipeListFragmentToRecipeDetailFragment(item.recipeId)
                requireView().findNavController().navigate(action)
            }
        })

        recipeList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = recipeAdapter
        }

        viewModel.recipeList.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading -> {
                    notFound.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    notFound.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    it.data?.let { list ->

                        if (list.isNotEmpty())
                            recipeAdapter.submitList(list)
                        else {
                            notFound.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            Snackbar.make(
                                requireView(),
                                "Something went wrong! Check your Internet connection",
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setAction(getString(R.string.ok)) { }
                                .show()
                        }

                    }
                }
                is Resource.Error -> {
                    notFound.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Snackbar.make(
                        requireView(),
                        "Something went wrong! Check your Internet connection",
                        Snackbar.LENGTH_INDEFINITE
                    )
                        .setAction(getString(R.string.ok)) { }
                        .show()
                }
            }

        }

    }
}