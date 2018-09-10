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
    var isTwoPane = false
    var feedType = MutableLiveData<FeedType>()
    var items = MutableLiveData<List<FeedItem>>()
    var articleId = MutableLiveData<String>()
    var article = MutableLiveData<Article>()
    var lceType = MutableLiveData<LceType>()
    private val compositeDisposable = CompositeDisposable()

    init {
        feedType.value = FeedType.ALL
    }

    fun loadFeed() {
        feedType.value?.let { feedType ->
            val disposable = repository.fetchFeed(feedType)
                    .doOnSubscribe { onLoading() }
                    .doAfterSuccess { onLoadSuccess() }
                    .subscribe({ items.value = it }, { onLoadError() })

            compositeDisposable.add(disposable)
        }
    }

    fun loadArticle() {
        articleId.value?.let { selectedArticle ->
            val disposable = repository.fetchArticle(selectedArticle)
                    .doOnSubscribe { onLoading() }
                    .doAfterSuccess { onLoadSuccess() }
                    .subscribe({ article.value = it }, { onLoadError() })

            compositeDisposable.add(disposable)
        }

    }

    private fun onLoading() {
        lceType.value = LceType.LOADING
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