package com.arjun.recipe.recipeList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.google.android.material.button.MaterialButton
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
    private lateinit var retry: MaterialButton

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

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
        retry = binding.retryButton

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

        //Hardcoded value for simplicity
        viewModel.getRecipes("chicken")

        retry.setOnClickListener {
            //Hardcoded value for simplicity
            viewModel.getRecipes("chicken")
        }

        viewModel.recipeList.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { list -> recipeAdapter.submitList(list) }
                }
                is Resource.Error -> {
                    retry.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Snackbar.make(requireView(), "Something went wrong!", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }

        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Timber.d("onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.d("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.d("onDetach")
    }

}