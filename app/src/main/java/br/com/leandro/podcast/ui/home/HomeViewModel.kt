package br.com.leandro.podcast.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.leandro.podcast.domain.GetHistoriesUseCase
import br.com.leandro.podcast.model.HistoryItem
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHistoriesUseCase: GetHistoriesUseCase
) : ViewModel() {

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
    fun checkRssLink(link: String): Boolean {
        addHistory(link, link)
        return true
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