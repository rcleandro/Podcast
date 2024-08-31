package br.com.leandro.podcast.core.repository

import android.util.Log
import br.com.leandro.podcast.core.database.AppDatabase
import br.com.leandro.podcast.core.database.entity.History
import br.com.leandro.podcast.core.model.HistoryDomain
import java.util.UUID

/**
 * Implementation of [HistoryRepository].
 *
 * @param appDatabase Database instance.
 * @constructor Creates a HistoryRepositoryImpl.
 */
class HistoryRepositoryImpl(appDatabase: AppDatabase) : HistoryRepository {

    private val dao = appDatabase.historyDao()

    override suspend fun fetchAll(): List<HistoryDomain> {
        Log.d(TAG, "Fetching all history")
        return dao.getAll().map {
            HistoryDomain(
                id = it.uuid,
                title = it.title,
                link = it.link
            )
        }
    }

    override suspend fun add(title: String, link: String) {
        Log.d(TAG, "Adding new history: $title")
        val history = History(
            uuid = UUID.randomUUID().toString(),
            title = title,
            link = link
        )
        dao.insert(history = history)
    }

    override suspend fun deleteAll() {
        Log.d(TAG, "Deleting all history")
        dao.deleteAll()
    }

    companion object {
        private const val TAG = "HistoryRepository"
    }
}