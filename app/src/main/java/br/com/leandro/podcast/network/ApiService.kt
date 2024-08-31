package br.com.leandro.podcast.network

import br.com.leandro.podcast.model.Feed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit Interface to fetch RSS Feed.
 *
 * @param endpoint: The endpoint to fetch the RSS Feed.
 * @return A Feed object containing the RSS Feed.
 */
interface ApiService {
    @GET("{endpoint}")
    fun getRssFeed(@Path("endpoint") endpoint: String): Call<Feed>
}