package br.com.leandro.podcast.repository

import br.com.leandro.podcast.model.ResponseClass
import br.com.leandro.podcast.network.OperationCallback
import br.com.leandro.podcast.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository implementation for fetching RSS feed.
 *
 * @property call: Call<Feed>? The call object for the request.
 * @constructor Creates a new instance of RssRepositoryImpl.
 */
class RssRepositoryImpl: RssRepository {
    private var call: Call<ResponseClass>?=null

    override suspend fun fetchFeed(url: String, callback: OperationCallback<ResponseClass>) {
        val client = RetrofitClient.build()
        this.call = client?.getRssFeed(url)

        call?.enqueue(
            object : Callback<ResponseClass> {
                override fun onResponse(call: Call<ResponseClass>, response: Response<ResponseClass>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.onSuccess(it)
                        } ?: callback.onError("Data is null")
                    } else callback.onError("Error ${response.code()}")
                }

                override fun onFailure(call: Call<ResponseClass>, t: Throwable) {
                    callback.onError(t.message)
                }
            }
        )
    }

    override fun cancel() {
        call?.cancel()
    }
}