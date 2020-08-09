package com.arjun.recipe.base

import androidx.fragment.app.Fragment
import coil.ImageLoader
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var imageLoader: ImageLoader

}