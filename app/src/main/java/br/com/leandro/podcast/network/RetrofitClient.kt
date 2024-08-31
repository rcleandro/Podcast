package br.com.leandro.podcast.network

import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitClient {

    fun build(url: String): ApiService? {
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}