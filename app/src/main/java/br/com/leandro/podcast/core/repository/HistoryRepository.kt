package br.com.leandro.podcast.core.repository

import br.com.leandro.podcast.core.model.HistoryDomain

interface HistoryRepository {

    suspend fun fetchAll(): List<HistoryDomain>

    suspend fun add(title: String, link: String)

    suspend fun deleteAll()
}