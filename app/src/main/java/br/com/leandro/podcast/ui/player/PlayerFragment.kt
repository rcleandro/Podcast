package br.com.leandro.podcast.ui.player

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import br.com.leandro.podcast.MainViewModel
import br.com.leandro.podcast.R
import br.com.leandro.podcast.databinding.FragmentPlayerBinding
import br.com.leandro.podcast.utils.htmlTextToString
import br.com.leandro.podcast.utils.toDateString
import br.com.leandro.podcast.utils.toDurationTime
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * PlayerFragment.
 *
 * Fragment responsible for playing the podcast episode.
 */
class PlayerFragment : Fragment(), Player.Listener {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private var progressUpdateJob: Job? = null

    private val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(requireContext()).build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initializePlayer()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.podcast.observe(viewLifecycleOwner) { podcast ->
            binding.textViewTitle.text = podcast.title.htmlTextToString()
            binding.textViewDescription.text = podcast.description.htmlTextToString()
            binding.textViewDuration.text = podcast.enclosure.duration.toDurationTime()
            binding.textViewDate.text = podcast.pubDate.toDateString()
            val authors = binding.root.resources.getString(R.string.authors, podcast.author)
            binding.textViewAuthors.text = authors

            if (podcast.thumbnail.isNotEmpty()) {
                Picasso.get()
                    .load(podcast.thumbnail)
                    .placeholder(R.drawable.ic_placeholder_24dp)
                    .into(binding.imageView)
            }

            val currentEpisodeIndex = mainViewModel.podcastList.value?.indexOf(podcast) ?: 0
            mainViewModel.setMediaItemIndex(currentEpisodeIndex)

            loadEpisode(podcast.enclosure.link)
        }
    }

    /**
     * Initialize Player.
     *
     * Load the episode and set the listeners.
     */
    private fun initializePlayer() {
        val podcast = mainViewModel.podcast.value ?: mainViewModel.podcastList.value?.get(0) ?: return
        loadEpisode(podcast.enclosure.link)

        binding.btnPlayPause.setOnClickListener {
            if (exoPlayer.isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
        }

        binding.btnNext.setOnClickListener {
            val currentEpisodeIndex = mainViewModel.mediaItemsIndex.value ?: 0
            val podcastList = mainViewModel.podcastList.value ?: emptyList()
            if (currentEpisodeIndex < podcastList.size - 1) {
                mainViewModel.setPodcast(podcastList[currentEpisodeIndex + 1])
                mainViewModel.setMediaCurrentPosition(0)
            }
        }

        binding.btnPrevious.setOnClickListener {
            val currentEpisodeIndex = mainViewModel.mediaItemsIndex.value ?: 0
            if (currentEpisodeIndex > 0) {
                val podcastList = mainViewModel.podcastList.value ?: emptyList()
                mainViewModel.setPodcast(podcastList[currentEpisodeIndex - 1])
                mainViewModel.setMediaCurrentPosition(0)
            }
        }

        binding.btnReplay10.setOnClickListener {
            val currentPosition = exoPlayer.currentPosition - 10000
            exoPlayer.seekTo(currentPosition)
        }

        binding.btnForward10.setOnClickListener {
            val currentPosition = exoPlayer.currentPosition + 10000
            exoPlayer.seekTo(currentPosition)
        }

        binding.slider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                val position = (exoPlayer.duration * value / 1000).toLong()
                exoPlayer.seekTo(position)
            }

            val progress = (exoPlayer.currentPosition / 1000).toInt()
            val duration = (exoPlayer.duration / 1000).toInt()
            binding.textViewProgressTime.text = progress.toDurationTime()
            binding.textViewTimeRemaining.text = (duration - progress).toDurationTime()
        }

        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    binding.slider.value = 0f
                    exoPlayer.seekTo(0)
                    exoPlayer.pause()
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                if (isPlaying) {
                    startProgressBarUpdater()
                    binding.btnPlayPause.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_pause_72dp
                        )
                    )
                } else {
                    progressUpdateJob?.cancel()
                    binding.btnPlayPause.setImageDrawable(
                        AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.ic_play_arrow_72dp
                        )
                    )
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Log.d("PlayerFragment", "onPlayerError: ${error.message}")
            }
        })
    }

    /**
     * Load the episode.
     *
     * @param url Episode URL.
     */
    private fun loadEpisode(url: String) {
        val mediaItem = MediaItem.fromUri(url)
        exoPlayer.setMediaItem(mediaItem, mainViewModel.mediaCurrentPosition.value ?: 0)
        exoPlayer.addListener(this)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    /**
     * Start the progress bar updater.
     */
    private fun startProgressBarUpdater() {
        progressUpdateJob?.cancel()
        progressUpdateJob = lifecycleScope.launch {
            while (exoPlayer.isPlaying) {
                val progress = (exoPlayer.currentPosition * 1000 / exoPlayer.duration).toFloat()

                if (progress <= 1000) {
                    binding.slider.value = progress
                }

                delay(1000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.setMediaCurrentPosition(exoPlayer.currentPosition)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        exoPlayer.release()
        progressUpdateJob?.cancel()
    }
}