package com.serefa.themovieapp.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.serefa.themovieapp.data.model.movie.Movie
import com.serefa.themovieapp.databinding.FragmentHomeBinding
import com.serefa.themovieapp.feature.common.BaseFragment
import com.serefa.themovieapp.feature.home.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: CategoryAdapter by lazy { CategoryAdapter(::onMovieClicked) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.recyclerViewCategories.adapter = adapter
        adapter.submitList(viewModel.getCategories())
    }

    private fun observeData(){
        viewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        popularMovies.collect { state ->
                            adapter.notifyItemChanged(0)
                        }
                    }
                    launch {
                        topRatedMovies.collect { state ->
                            adapter.notifyItemChanged(1)
                        }
                    }
                    launch {
                        revenueMovies.collect { state ->
                            adapter.notifyItemChanged(2)
                        }
                    }
                    launch {
                        releaseDateMovies.collect { state ->
                            adapter.notifyItemChanged(3)
                        }
                    }
                }
            }
        }
    }

    private fun onMovieClicked(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(movie.id.toString())
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}