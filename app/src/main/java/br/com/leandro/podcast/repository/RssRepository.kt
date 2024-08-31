package br.com.leandro.podcast.repository

import br.com.leandro.podcast.model.Feed
import br.com.leandro.podcast.network.OperationCallback

interface RssRepository {
    suspend fun fetchFeed(url: String, callback: OperationCallback<Feed>)
    fun cancel()
}