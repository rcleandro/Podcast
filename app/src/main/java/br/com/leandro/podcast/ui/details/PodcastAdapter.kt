package br.com.leandro.podcast.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.podcast.R
import br.com.leandro.podcast.databinding.ItemPodcastBinding
import br.com.leandro.podcast.model.Podcast
import br.com.leandro.podcast.utils.htmlTextToString
import br.com.leandro.podcast.utils.toDateString
import br.com.leandro.podcast.utils.toDurationTime
import com.squareup.picasso.Picasso

class PodcastAdapter(
    private val onItemClicked: (Podcast) -> Unit,
) : RecyclerView.Adapter<PodcastAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<Podcast> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPodcastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun updatePodcasts(podcasts: List<Podcast>) {
        asyncListDiffer.submitList(podcasts)
    }

    class ViewHolder(
        private val binding: ItemPodcastBinding,
        private val onItemClicked: (Podcast) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(podcast: Podcast) {
            binding.textViewPodcastTitle.text = podcast.title.htmlTextToString()
            val authors = binding.root.resources.getString(R.string.authors, podcast.author)
            binding.textViewAuthor.text = authors
            binding.textViewDescription.text = podcast.description.htmlTextToString()
            binding.textViewDuration.text = podcast.enclosure.duration.toDurationTime()
            binding.textViewDate.text = podcast.pubDate.toDateString()

            if (podcast.thumbnail.isNotEmpty()) {
                Picasso.get()
                    .load(podcast.thumbnail)
                    .placeholder(R.drawable.ic_details_black_24dp)
                    .into(binding.imageView)
            }

            binding.root.setOnClickListener {
                onItemClicked(podcast)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Podcast>() {

        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.title == newItem.title
        }
    }
}