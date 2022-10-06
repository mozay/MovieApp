package com.mozay.movieapp.presentation.detail

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mozay.movieapp.common.extensions.gone
import com.mozay.movieapp.common.extensions.showSnackBar
import com.mozay.movieapp.common.extensions.visible
import com.mozay.movieapp.data.model.EventObserver
import com.mozay.movieapp.databinding.FragmentMovieDetailsBinding
import com.mozay.movieapp.presentation.base.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_details.*


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment(true) {

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var viewDataBinding: FragmentMovieDetailsBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
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
        viewModel.getMovieDetail(args.movieIdArg)
        addObserver()
    }

    private fun addObserver(){
        viewModel.videos?.observe(viewLifecycleOwner) {
            it?.let { videos ->
                if (videos.isNotEmpty()) {
                    movieVideoProgressBar.gone()
                    movieTrailer.visible()
                    initYoutubePlayer(videos.first().key)
                } else movieTrailer.gone()
            }
        }
    }

    /** API şuanda trailer için youtube linkleri dönüyor.
    ExoPlayer doğrudan youtube videoları oynatamıyor.
    Extractor librarys kullanarak bunu yapmak mümkün fakat library şuan extract işlemlerinde hata veriyor.
    Konuyla ilgili açılmış issue'lar mevcut.
    O yüzden farklı bir media player kullanmak durumunda kaldım.
     * */
    private fun initYoutubePlayer(key: String){
        lifecycle.addObserver(movieTrailer)
        val listener = object : AbstractYouTubePlayerListener() {
            override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                // using pre-made custom ui
                val defaultPlayerUiController =
                    DefaultPlayerUiController(movieTrailer, youTubePlayer)
                movieTrailer.setCustomPlayerUi(defaultPlayerUiController.rootView)
                youTubePlayer.cueVideo(key, 0f)
            }
        }
        movieTrailer.addYouTubePlayerListener(listener)

        val fullScreenListener = object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
                moviesLayout.gone()

            }

            override fun onYouTubePlayerExitFullScreen() {
                moviesLayout.visible()
            }
        }

        movieTrailer.addFullScreenListener(fullScreenListener)

        // disable iframe ui
        val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
        movieTrailer.initialize(listener, options)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val currentOrientation = resources.configuration.orientation
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            movieTrailer.enterFullScreen()
        } else {
            movieTrailer.exitFullScreen()
        }
    }

    override fun setupViewModelObservers() {
        viewModel.snackBarText.observe(viewLifecycleOwner, EventObserver { view?.showSnackBar(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        try { movieTrailer.release() }catch (ignored: Exception){ }
    }
}