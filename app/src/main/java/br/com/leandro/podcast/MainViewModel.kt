package br.com.leandro.podcast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.leandro.podcast.model.Feed
import br.com.leandro.podcast.model.Podcast

/**
 * MainViewModel.
 *
 * ViewModel responsible for sharing data between fragments.
 */
class MainViewModel: ViewModel() {
    private val _feed = MutableLiveData<Feed>()
    val feed: LiveData<Feed> = _feed

    private val _podcastList = MutableLiveData<List<Podcast>>()
    val podcastList: LiveData<List<Podcast>> = _podcastList

    private val _podcast = MutableLiveData<Podcast>()
    val podcast: LiveData<Podcast> = _podcast

    private val _mediaItemsIndex = MutableLiveData<Int>()
    val mediaItemsIndex: LiveData<Int> = _mediaItemsIndex

    private val _mediaCurrentPosition = MutableLiveData<Long>()
    val mediaCurrentPosition: LiveData<Long> = _mediaCurrentPosition

    fun setFeed(feed: Feed) = _feed.postValue(feed)

    fun setPodcastList(podcastList: List<Podcast>) = _podcastList.postValue(podcastList)

    fun setPodcast(podcast: Podcast) = _podcast.postValue(podcast)

    fun setMediaItemIndex(index: Int) = _mediaItemsIndex.postValue(index)

    fun setMediaCurrentPosition(position: Long) = _mediaCurrentPosition.postValue(position)
}