package com.serefa.themovieapp.feature.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.drm.DefaultDrmSessionManagerProvider
import com.serefa.themovieapp.databinding.FragmentPlayerBinding
import com.serefa.themovieapp.feature.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@androidx.media3.common.util.UnstableApi
class PlayerFragment : BaseFragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = PlayerFragmentArgs.fromBundle(requireArguments())
        binding.textViewMovieDesc.text = args.desc
        initializePlayer()
    }

    private fun initializePlayer() {

        if (viewModel.exoPlayer == null) {
            viewModel.exoPlayer = ExoPlayer.Builder(requireContext()).build()

            val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
            val drmConfig = MediaItem.DrmConfiguration.Builder(C.WIDEVINE_UUID)
                .setLicenseUri(DRM_LICENSE_URL)
            val mediaItem = MediaItem.Builder()
                .setUri(VIDEO_URL)
                .setDrmConfiguration(drmConfig.build())

            val mediaSource = DashMediaSource.Factory(defaultHttpDataSourceFactory)
                .createMediaSource(mediaItem.build())
            viewModel.exoPlayer!!.setMediaSource(mediaSource)
            viewModel.exoPlayer!!.prepare()
            viewModel.exoPlayer!!.playWhenReady = true
        }
        binding.playerView.player = viewModel.exoPlayer
    }

    override fun onDestroy() {
        super.onDestroy()
    }

//    override fun onConfigurationChanged(newConfig: Configuration) {
//        super.onConfigurationChanged(newConfig)
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // Switch to full screen
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            // Exit full screen
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val VIDEO_URL =
            "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
        private const val DRM_LICENSE_URL =
            "https://proxy.uat.widevine.com/proxy?video_id=2015_tears&provider=widevine_test"
    }
}