package br.com.leandro.podcast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.podcast.databinding.ItemHistoryBinding
import br.com.leandro.podcast.model.HistoryItem

/**
 * RecyclerView adapter for displaying a list of Histories.
 *
 * The UI is based on the [HistoryAdapter].
 * We use the [HistoryItem] as a model for the binding.
 */
class HistoryAdapter(
    private val onItemClicked: (HistoryItem) -> Unit,
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<HistoryItem> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun updateHistories(histories: List<HistoryItem>) {
        asyncListDiffer.submitList(histories)
    }

    class ViewHolder(
        private val binding: ItemHistoryBinding,
        private val onItemClicked: (HistoryItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryItem) {
            binding.textViewHistoryTitle.text = history.title

            binding.root.setOnClickListener {
                onItemClicked(history)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<HistoryItem>() {

        override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
            return oldItem.id == newItem.id
        }
    }
}