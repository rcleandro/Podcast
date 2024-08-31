package br.com.leandro.podcast.domain

import br.com.leandro.podcast.model.HistoryItem

interface GetHistoriesUseCase {

    suspend operator fun invoke(): List<HistoryItem>

    suspend fun add(title: String, link: String)

    suspend fun deleteAll()
}