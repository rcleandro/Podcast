package br.com.leandro.podcast.network

/**
 * Interface to handle the success and error callbacks from the API.
 *
 * @param T: The type of the data to be returned.
 * @return The data if the operation was successful, or an error message if it wasn't.
 */
interface OperationCallback<T> {
    fun onSuccess(data: T)
    fun onError(error:String?)
}