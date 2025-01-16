package com.serefa.themovieapp.feature.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.serefa.themovieapp.data.model.movie.detail.MovieDetailResponse
import com.serefa.themovieapp.data.model.movie.local.ImageQuality
import com.serefa.themovieapp.databinding.FragmentMovieDetailBinding
import com.serefa.themovieapp.extensions.load
import com.serefa.themovieapp.feature.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = MovieDetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getMovieDetail(args.movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding.imageViewPlay.setOnClickListener {
            val action = MovieDetailFragmentDirections.
            actionDetailFragmentToPlayerFragment(
                viewModel.movieDetail.value.overview?:"")
            findNavController().navigate(action)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.apply {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    movieDetail.collect { movieDetailResponse ->
                        initUI(movieDetailResponse)
                    }
                }
            }
        }
    }

    private fun initUI(movieDetailResponse: MovieDetailResponse) {
        binding.apply {
            movieDetailResponse.getThumbUrl(ImageQuality.LARGE)?.let {
                imageViewLargePoster.load(it)
            }
            textviewMovieName.text = movieDetailResponse.title
            textViewRate.text = movieDetailResponse.voteAverage.toString()
            textViewDate.text = movieDetailResponse.releaseDate
            textViewMovieDesc.text = movieDetailResponse.overview
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}