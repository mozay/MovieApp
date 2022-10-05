package com.mozay.movieapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mozay.movieapp.common.extensions.showSnackBar
import com.mozay.movieapp.data.model.EventObserver
import com.mozay.movieapp.databinding.FragmentHomeBinding
import com.mozay.movieapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(false) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentHomeBinding.inflate(inflater, container, false).apply {
                viewmodel = viewModel
                lifecycleOwner = this@HomeFragment.viewLifecycleOwner
            }
        return viewDataBinding.root
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
        viewModel.goToMovieDetailsEvent.observe(
            viewLifecycleOwner,
            EventObserver { navigateToMovieDetails(it.id, it.title) })
    }

    private fun navigateToMovieDetails(movieId: Int, movieTitle: String) {
        val action =
            HomeFragmentDirections.actionNavigationHomeToMovieDetailsFragment(movieId, movieTitle)
        findNavController().navigate(action)
    }
}

