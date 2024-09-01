package br.com.leandro.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.leandro.podcast.model.Feed
import br.com.leandro.podcast.model.Podcast

class MainViewModel: ViewModel() {
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _podcastList = MutableLiveData<List<Podcast>>()
    val podcastList: LiveData<List<Podcast>> = _podcastList

    fun setFeed(feed: Feed) = _feed.postValue(feed)
    fun setPodcastList(podcastList: List<Podcast>) = _podcastList.postValue(podcastList)
}