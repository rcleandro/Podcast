package br.com.leandro.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.leandro.podcast.model.Feed

class MainViewModel: ViewModel() {
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    fun setFeed(feed: Feed) = _feed.postValue(feed)
}