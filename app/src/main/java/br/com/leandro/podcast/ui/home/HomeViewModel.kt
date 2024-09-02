package br.com.leandro.podcast.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.leandro.podcast.domain.GetHistoriesUseCase
import br.com.leandro.podcast.model.Feed
import br.com.leandro.podcast.model.HistoryItem
import br.com.leandro.podcast.model.Podcast
import br.com.leandro.podcast.model.ResponseClass
import br.com.leandro.podcast.network.OperationCallback
import br.com.leandro.podcast.repository.RssRepositoryImpl
import br.com.leandro.podcast.utils.toRssUrl
import kotlinx.coroutines.launch

/**
 * HomeViewModel.
 *
 * ViewModel responsible for the HomeFragment.
 */
class HomeViewModel(
    private val getHistoriesUseCase: GetHistoriesUseCase
) : ViewModel() {

    private val repository = RssRepositoryImpl()

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(historyItemList = emptyList()))
    }

    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _podcastList = MutableLiveData<List<Podcast>>()
    val podcastList: LiveData<List<Podcast>> = _podcastList

    private val _error = MutableLiveData<Boolean>().apply { value = false }
    val error: LiveData<Boolean> = _error

    fun setError(error: Boolean) = _error.postValue(error)

    /**
     * Refresh UI State whenever View Resumes.
     */
    fun onResume() {
        viewModelScope.launch {
            refreshHistoriesList()
        }
    }

    /**
     * Expose the uiState as LiveData to UI.
     */
    fun stateOnceAndStream(): LiveData<UiState> {
        return uiState
    }

    /**
     * Refresh UI State with new Histories.
     */
    private suspend fun refreshHistoriesList() {
        uiState.postValue(UiState(getHistoriesUseCase.invoke()))
    }

    /**
     * UI State containing every data needed to show Histories.
     */
    data class UiState(val historyItemList: List<HistoryItem>)

    /**
     * Check if the link is a valid RSS link.
     *
     * @param link: The link you wanna check if is a valid RSS link.
     * @return true if the link is a valid RSS link, false otherwise.
     */
    fun checkRssLink(link: String, addHistory: Boolean = false) {
        viewModelScope.launch {
            val url = link.toRssUrl()
            Log.d("HomeViewModel", "Fetching RSS feed from URL: $url")

            repository.fetchFeed(url, object : OperationCallback<ResponseClass> {
                override fun onSuccess(data: ResponseClass) {
                    if (addHistory)
                        addHistory(data.feed.title, link)

                    _podcastList.postValue(data.podcastList)
                    _feed.postValue(data.feed)
                    Log.d("HomeViewModel", "Channel Title: ${data.feed.title}")
                }

                override fun onError(error: String?) {
                    Log.e("HomeViewModelHomeViewModel", "Error: $error")
                    setError(true)
                }
            })
        }
    }

    /**
     * Add new History.
     *
     * @param name: The name you wanna give to this History
     * @param link: The link you wanna give to this History
     */
    private fun addHistory(name: String, link: String) {
        viewModelScope.launch {
            getHistoriesUseCase.add(name, link)
            refreshHistoriesList()
        }
    }

    /**
     * Delete all Histories.
     *
     * This will delete all Histories from the database.
     */
    fun deleteAllHistory() {
        viewModelScope.launch {
            getHistoriesUseCase.deleteAll()
            refreshHistoriesList()
        }
    }

    /**
     * ViewModel Factory needed to provide Repository injection to ViewModel.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val getHistoriesUseCase: GetHistoriesUseCase,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(getHistoriesUseCase) as T
        }
    }
}