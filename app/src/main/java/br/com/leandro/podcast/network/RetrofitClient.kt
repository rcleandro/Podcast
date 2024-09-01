package br.com.leandro.podcast.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Object to build the Retrofit client.
 *
 * @property BASE_URL the base URL for the API.
 * @constructor Creates a Retrofit client.
 */
object RetrofitClient {

    /**
     * The base URL for the API.
     */
    private const val BASE_URL = "https://api.rss2json.com/"

    fun build(): ApiService? {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}