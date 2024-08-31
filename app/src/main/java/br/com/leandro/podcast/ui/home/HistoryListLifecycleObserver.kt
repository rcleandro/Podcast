package br.com.leandro.podcast.ui.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Lifecycle Observer responsible for handling the HomeFragment lifecycle.
 *
 * @param viewModel ViewModel responsible for handling the HomeFragment data.
 * @constructor Creates a HistoryListLifecycleObserver.
 */
class HistoryListLifecycleObserver(
    private val viewModel: HomeViewModel
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        viewModel.onResume()
    }
}
