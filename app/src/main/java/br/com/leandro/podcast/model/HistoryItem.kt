package br.com.leandro.podcast.model

import java.io.Serializable

/**
 * History Model representing an Item in a ListView.
 *
 * @param id the id of the History
 * @param title the title of the History
 * @param link the link of the History
 *
 */
data class HistoryItem(
    val id: String,
    val title: String,
    val link: String
)