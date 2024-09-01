package br.com.leandro.podcast.network

import br.com.leandro.podcast.model.ResponseClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit Interface to fetch RSS Feed.
 */
interface ApiService {
    @GET("/v1/api.json")
    fun getRssFeed(@Query("rss_url") query :String): Call<ResponseClass>
}