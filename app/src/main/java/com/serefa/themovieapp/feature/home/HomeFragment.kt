package com.serefa.themovieapp.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
    private val adapter: CategoryAdapter by lazy { CategoryAdapter() }

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
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}