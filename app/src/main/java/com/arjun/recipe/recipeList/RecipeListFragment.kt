package com.arjun.recipe.recipeList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.recipe.R
import com.arjun.recipe.base.BaseFragment
import com.arjun.recipe.databinding.FragmentRecipeListBinding
import com.arjun.recipe.model.Recipe
import com.arjun.recipe.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class RecipeListFragment : BaseFragment() {

    private val binding: FragmentRecipeListBinding by viewBinding(FragmentRecipeListBinding::bind)

    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var recipeAdapter: RecipeListAdapter
    private lateinit var recipeList: RecyclerView
    private lateinit var retry: Button

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeList = binding.recipeList
        retry = binding.retryButton

        recipeAdapter = RecipeListAdapter(imageLoader, object : Interaction {
            override fun onItemSelected(position: Int, item: Recipe) {
                Timber.d("${item.title} at $position")
            }
        })

        recipeAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { recipeAdapter.retry() },
            footer = LoadStateAdapter { recipeAdapter.retry() }
        )

        recipeAdapter.addLoadStateListener { loadState ->

            binding.progressBar.isVisible =
                loadState.refresh is LoadState.Loading
            binding.retryButton.isVisible =
                loadState.refresh is LoadState.Error

            val errorState = when {
                loadState.append is LoadState.Error -> {
                    loadState.append as LoadState.Error
                }
                loadState.prepend is LoadState.Error -> {
                    loadState.prepend as LoadState.Error
                }
                else -> {
                    null
                }
            }
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        load()

        retry.setOnClickListener { recipeAdapter.retry() }

        recipeList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = recipeAdapter
        }


    }

    private fun load() {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRecipe("chicken").collectLatest {
                recipeAdapter.submitData(it)
            }
        }
    }

}