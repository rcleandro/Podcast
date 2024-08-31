package br.com.leandro.podcast.network

interface OperationCallback<T> {
    fun onSuccess(data: T)
    fun onError(error:String?)
}