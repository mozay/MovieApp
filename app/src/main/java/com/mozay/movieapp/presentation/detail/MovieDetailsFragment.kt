package com.mozay.movieapp.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.*
import com.mozay.movieapp.common.extensions.showSnackBar
import com.mozay.movieapp.data.model.EventObserver
import com.mozay.movieapp.databinding.FragmentMovieDetailsBinding
import com.mozay.movieapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(true) {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentMovieDetailsBinding

    private var exoPlayer: SimpleExoPlayer? = null
    private var playWhenReady: Boolean = true
    private var currentWindow: Int = 0
    private var playbackPosition : Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding =
            FragmentMovieDetailsBinding.inflate(inflater, container, false)
                .apply {
                    viewmodel = viewModel
                    lifecycleOwner = this@MovieDetailsFragment.viewLifecycleOwner
                }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializePlayer()
        viewModel.getMovieDetail(args.movieIdArg)
    }

    @SuppressLint("StaticFieldLeak")
    private fun initializePlayer(){
        context?.let {
            val url = "https://www.youtube.com/watch?v=FNk7Cu4sJOs&html5=1"
            exoPlayer = SimpleExoPlayer.Builder(it).build()
            movieTrailer.player = exoPlayer

            val extractor = object : YouTubeExtractor(it){
                override fun onExtractionComplete(
                    ytFiles: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {
                    if (ytFiles != null){
                        val videoTag = 137
                        val audioTag = 140
                        val audioSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(audioTag).url))
                        val videoSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles.get(videoTag).url))
                        exoPlayer?.setMediaSource(MergingMediaSource(true, videoSource, audioSource), true)
                        exoPlayer?.prepare()
                        exoPlayer?.playWhenReady = playWhenReady
                        exoPlayer?.seekTo(currentWindow, playbackPosition)
                    }
                }

            }.extract(url, false, true)
        }
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            player.release()
            exoPlayer = null
        }
    }



    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }
}