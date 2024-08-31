package br.com.leandro.podcast.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.podcast.databinding.ItemHistoryBinding

/**
 * RecyclerView adapter for displaying a list of Histories.
 *
 * The UI is based on the [HistoryAdapter].
 * We use the [String] as a model for the binding.
 */
class HistoryAdapter(
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<String> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun updateHistories(histories: List<String>) {
        asyncListDiffer.submitList(histories)
    }

    class ViewHolder(
        private val binding: ItemHistoryBinding,
        private val onItemClicked: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: String) {
            binding.textViewHistoryTitle.text = history

            binding.root.setOnClickListener {
                onItemClicked(history)
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}