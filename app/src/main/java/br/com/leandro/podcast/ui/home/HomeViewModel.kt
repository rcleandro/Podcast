package br.com.leandro.podcast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val uiState: MutableLiveData<UiState> by lazy {
        MutableLiveData<UiState>(UiState(historyItemList = emptyList()))
    }

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

    private fun refreshHistoriesList() {
        uiState.postValue(UiState(listOf("History 1", "History 2", "History 3")))
    }

    /**
     * UI State containing every data needed to show Histories.
     */
    data class UiState(val historyItemList: List<String>)

    fun checkRssLink(rssLink: String): Boolean {
        return rssLink.isNotEmpty()
    }
}