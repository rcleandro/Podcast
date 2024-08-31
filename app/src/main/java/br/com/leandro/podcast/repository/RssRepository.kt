package br.com.leandro.podcast.repository

import br.com.leandro.podcast.model.Feed
import br.com.leandro.podcast.network.OperationCallback

/**
 * Repository to fetch RSS Feed.
 *
 * @property fetchFeed Fetch the RSS Feed from the given URL.
 */
interface RssRepository {
    suspend fun fetchFeed(url: String, callback: OperationCallback<Feed>)
    fun cancel()
}