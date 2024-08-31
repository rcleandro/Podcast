package br.com.leandro.podcast.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * Object to build the Retrofit client.
 *
 * @param build: Builds the Retrofit client with the base URL.
 * @param url: The base URL to be used by the Retrofit client.
 * @return An instance of the ApiService interface.
 */
object RetrofitClient {

    fun build(url: String): ApiService? {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}