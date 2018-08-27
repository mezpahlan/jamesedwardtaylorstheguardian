package uk.co.mezpahlan.oldtimerag.theguardian.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import uk.co.mezpahlan.oldtimerag.base.LceType
import uk.co.mezpahlan.oldtimerag.theguardian.article.Article
import uk.co.mezpahlan.oldtimerag.theguardian.data.TheGuardianRepository
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedItem
import uk.co.mezpahlan.oldtimerag.theguardian.feed.FeedType

class SharedViewModel(private val repository: TheGuardianRepository) : ViewModel() {
    var feedType = FeedType.ALL
    var isTwoPane = false
    var items = MutableLiveData<List<FeedItem>>()
    var selected = MutableLiveData<String?>()
    var article = MutableLiveData<Article>()
    var lceType = MutableLiveData<LceType>()
    private val compositeDisposable = CompositeDisposable()

    init {
        loadFeed(feedType)
    }

    fun loadFeed(feedType: FeedType) {
        lceType.value = LceType.LOADING
        val disposable = repository.fetchFeed(feedType)
                .doAfterSuccess { onLoadSuccess() }
                .subscribe({ items.value = it }, { onLoadError() })

        compositeDisposable.add(disposable)
    }

    fun loadArticle(id: String) {
        lceType.value = LceType.LOADING
        val disposable = repository.fetchArticle(id)
                .doAfterSuccess { onLoadSuccess() }
                .subscribe({ article.value = it }, { onLoadError() })

        compositeDisposable.add(disposable)
    }

    private fun onLoadSuccess() {
        lceType.value = LceType.CONTENT
    }

    private fun onLoadError() {
        lceType.value = LceType.ERROR
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}