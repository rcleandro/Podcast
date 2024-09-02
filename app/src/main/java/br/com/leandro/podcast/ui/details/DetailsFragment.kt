package br.com.leandro.podcast.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.leandro.podcast.MainViewModel
import br.com.leandro.podcast.R
import br.com.leandro.podcast.databinding.FragmentDetailsBinding
import br.com.leandro.podcast.model.Podcast
import br.com.leandro.podcast.utils.htmlTextToString
import com.squareup.picasso.Picasso

/**
 * DetailsFragment.
 *
 * Fragment responsible for displaying the details of the podcast.
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: PodcastAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = PodcastAdapter { podcast ->
            navigateToPlayer(podcast)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set the adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Observe Feed for changes.
        mainViewModel.feed.observe(viewLifecycleOwner) { feed ->
            binding.textViewTitle.text = feed.title.htmlTextToString()
            binding.textViewDescription.text = feed.description.htmlTextToString()

            Picasso.get()
                .load(feed.image)
                .placeholder(R.drawable.ic_placeholder_24dp)
                .into(binding.imageView)
        }

        // Observe PodcastList for changes.
        mainViewModel.podcastList.observe(viewLifecycleOwner) { podcastList ->
            adapter.updatePodcasts(podcastList)
        }
    }

    /**
     * Navigate to Player Screen.
     *
     * @param podcast Podcast
     */
    private fun navigateToPlayer(podcast: Podcast) {
        mainViewModel.setMediaCurrentPosition(0)
        mainViewModel.setPodcast(podcast)
        findNavController().navigate(R.id.action_navigation_details_to_navigation_player)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}