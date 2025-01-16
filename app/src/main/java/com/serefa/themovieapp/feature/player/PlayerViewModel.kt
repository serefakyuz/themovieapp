package com.serefa.themovieapp.feature.player

import androidx.media3.exoplayer.ExoPlayer
import com.serefa.themovieapp.feature.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(): BaseViewModel() {

    var exoPlayer: ExoPlayer? = null

    override fun onCleared() {
        super.onCleared()
        exoPlayer?.release()
        exoPlayer = null

    }
}