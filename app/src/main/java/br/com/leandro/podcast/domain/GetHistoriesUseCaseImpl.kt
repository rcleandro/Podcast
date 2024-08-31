package br.com.leandro.podcast.domain

import android.util.Log
import br.com.leandro.podcast.core.repository.HistoryRepository
import br.com.leandro.podcast.model.HistoryItem

class GetHistoriesUseCaseImpl(
    private val historyRepository: HistoryRepository
) : GetHistoriesUseCase {

    override suspend fun invoke(): List<HistoryItem> {
        Log.d(TAG, "Fetching all histories")

        return historyRepository
            .fetchAll()
            .map { history ->
                HistoryItem(
                    id = history.id,
                    title = history.title,
                    link = history.link
                )
            }
    }

    override suspend fun add(title: String, link: String) {
        Log.d(TAG, "Adding new history: $title")
        historyRepository.add(title, link)
    }

    override suspend fun deleteAll() {
        Log.d(TAG, "Deleting all histories")
        historyRepository.deleteAll()
    }

    companion object {
        private const val TAG = "GetHistoriesUseCase"
    }
}